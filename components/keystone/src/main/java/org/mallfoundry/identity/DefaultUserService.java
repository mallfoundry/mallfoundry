/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.identity;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.captcha.Captcha;
import org.mallfoundry.captcha.CaptchaException;
import org.mallfoundry.captcha.CaptchaService;
import org.mallfoundry.configuration.ConfigurationHolder;
import org.mallfoundry.configuration.ConfigurationKeys;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.CryptoUtils;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.mallfoundry.i18n.MessageHolder.message;

public class DefaultUserService implements UserService, UserProcessorInvoker, ApplicationEventPublisherAware {

    private static final String USER_ID_VALUE_NAME = "identity.user.id";

    private List<UserProcessor> processors;

    private final List<UserValidator> userValidators;

    private final CaptchaService captchaService;

    private ApplicationEventPublisher eventPublisher;

    private final UserRepository userRepository;

    public DefaultUserService(List<UserValidator> userValidators, CaptchaService captchaService, UserRepository userRepository) {
        this.userValidators = ListUtils.emptyIfNull(userValidators);
        this.captchaService = captchaService;
        this.userRepository = userRepository;
    }

    public void setProcessors(List<UserProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserId createUserId(String id) {
        return new ImmutableUserId(null, id);
    }

    @Override
    public UserId createUserId(String tenantId, String id) {
        return new ImmutableUserId(tenantId, id);
    }

    @Override
    public User createUser(UserId id) {
        return this.userRepository.create(id);
    }

    private void setDefaultUser(User user) {
        // 使用 Configuration 设置默认值。
        var config = ConfigurationHolder.getConfiguration(user);
        var defaultUsername = config.getString(ConfigurationKeys.IDENTITY_USER_DEFAULT_USERNAME);
        user.setUsername(UserReplacer.replace(defaultUsername, user));
        if (StringUtils.isBlank(user.getNickname())) {
            var defaultNickname = config.getString(ConfigurationKeys.IDENTITY_USER_DEFAULT_NICKNAME);
            user.setNickname(UserReplacer.replace(defaultNickname, user));
        }
        var defaultAvatar = config.getString(ConfigurationKeys.IDENTITY_USER_DEFAULT_AVATAR);
        user.setAvatar(defaultAvatar);
    }

    @Transactional
    @Override
    public User createUser(UserRegistration registration) {
        this.userValidators.forEach(userValidator -> userValidator.validateCreateUser(registration));
        var newUserId = this.createUserId(User.DEFAULT_TENANT_ID, PrimaryKeyHolder.next(USER_ID_VALUE_NAME));
        var user = registration.assignToUser(this.userRepository.create(newUserId));
        this.setDefaultUser(user);
        user.changePassword(this.encodePassword(registration.getPassword()));
        user.create();
        var savedUser = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserCreatedEvent(savedUser));
        return savedUser;
    }

    @Override
    public User getCurrentUser() {
        var subject = SubjectHolder.getSubject();
        return this.getUser(this.createUserId(subject.getTenantId(), subject.getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(UserId userId) {
        return this.findUser(userId)
                .orElseThrow(() -> UserExceptions.notExists(userId.getId()));
    }

    @Transactional(readOnly = true)
    public Optional<User> findUser(UserId userId) {
        return this.userRepository.findById(userId)
                .map(this::invokePostProcessAfterGetUser);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findUser(UserSearch search) {
        return this.userRepository.findBySearch(search)
                .map(this::invokePostProcessAfterGetUser);
    }

    @Transactional
    @Override
    public User updateUser(User source) {
        var user = this.getUser(source.toId());
        Copies.notBlank(source::getNickname).trim(user::setNickname);
        user = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserChangedEvent(user));
        return user;
    }

    private String encodePassword(String password) {
        return CryptoUtils.encode(password);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return CryptoUtils.matches(rawPassword, encodedPassword);
    }

    private User retakeUserPassword(User user, String password) {
        user.changePassword(this.encodePassword(password));
        user = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserCredentialsChangedEvent(user));
        return user;
    }

    private User getUser(UserRetake reset) {
        if (UserRetake.Mode.USERNAME_PASSWORD.equals(reset.getMode())) {
            return this.userRepository
                    .findBySearch(new UserSearch() {
                        @Override
                        public String getUsername() {
                            return reset.getUsername();
                        }
                    })
                    .orElseThrow(() -> UserExceptions.notExists(reset.getUsername()));
        } else if (UserRetake.Mode.PHONE_PASSWORD.equals(reset.getMode())) {
            return this.userRepository
                    .findBySearch(new UserSearch() {
                        @Override
                        public String getCountryCode() {
                            return reset.getCountryCode();
                        }

                        @Override
                        public String getPhone() {
                            return reset.getPhone();
                        }
                    })
                    .orElseThrow(() -> UserExceptions.notExists(reset.getPhone()));
        }
        throw UserExceptions.notExists("Not supported");
    }

    private User getUser(Captcha captcha) {
        var countryCode = captcha.getParameter(Captcha.COUNTRY_CODE_PARAMETER_NAME);
        var phone = captcha.getParameter(Captcha.PHONE_PARAMETER_NAME);
        return this.userRepository
                .findBySearch(new UserSearch() {
                    @Override
                    public String getCountryCode() {
                        return countryCode;
                    }

                    @Override
                    public String getPhone() {
                        return phone;
                    }
                })
                .orElseThrow(() -> UserExceptions.notExists(phone));
    }

    @Transactional
    @Override
    public User retakeUser(UserRetake reset) throws UserException {
        if (UserRetake.Mode.USERNAME_PASSWORD.equals(reset.getMode())
                || UserRetake.Mode.PHONE_PASSWORD.equals(reset.getMode())) {
            var user = this.getUser(reset);
            if (!this.matchesPassword(reset.getOriginalPassword(), user.getPassword())) {
                throw new UserException(
                        message("identity.user.originalPasswordIncorrect",
                                "The original password is incorrect"));
            }
            return this.retakeUserPassword(user, reset.getPassword());
        } else if (UserRetake.Mode.CAPTCHA.equals(reset.getMode())) {
            var captcha = this.captchaService.getCaptcha(reset.getCaptchaToken());
            var checked = this.captchaService.checkCaptcha(reset.getCaptchaToken(), reset.getCaptchaCode());
            if (!checked) {
                throw CaptchaException.INVALID_CAPTCHA;
            }
            var user = this.getUser(captcha);
            return this.retakeUserPassword(user, reset.getPassword());
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteUser(UserId userId) {
        var user = this.getUser(userId);
        this.eventPublisher.publishEvent(new ImmutableUserDeletedEvent(user));
        this.userRepository.delete(user);
    }

    @Override
    public User invokePostProcessAfterGetUser(User user) {
        return Processors.stream(processors)
                .map(UserProcessor::postProcessAfterGetUser)
                .apply(user);
    }
}
