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

    private final UserConfiguration userConfiguration;

    private List<UserProcessor> processors;

    private final List<UserValidator> userValidators;

    private ApplicationEventPublisher eventPublisher;

    private final UserRepository userRepository;

    public DefaultUserService(UserConfiguration userConfiguration,
                              List<UserValidator> userValidators,
                              UserRepository userRepository) {
        this.userConfiguration = userConfiguration;
        this.userValidators = ListUtils.emptyIfNull(userValidators);
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

    public void setDefaultUsername(User user) {
        var defaultUsername = this.userConfiguration.getDefaultUsername();
        user.setUsername(UserReplacer.replace(defaultUsername, user));
    }

    private void setDefaultNickname(User user) {
        if (StringUtils.isBlank(user.getNickname())) {
            var defaultNickname = this.userConfiguration.getDefaultNickname();
            user.setNickname(UserReplacer.replace(defaultNickname, user));
        }
    }

    @Transactional
    @Override
    public User createUser(UserRegistration registration) {
        this.userValidators.forEach(userValidator -> userValidator.validateCreateUser(registration));
        var newUserId = this.createUserId(User.DEFAULT_TENANT_ID, PrimaryKeyHolder.next(USER_ID_VALUE_NAME));
        var user = registration.assignToUser(this.userRepository.create(newUserId));
        this.setDefaultUsername(user);
        this.setDefaultNickname(user);
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
    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(this::invokePostProcessAfterGetUserByUsername);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findUserByPhone(String countryCode, String phone) {
        return this.userRepository.findByCountryCodeAndPhone(countryCode, phone)
                .map(this::invokePostProcessAfterGetUserByPhone);
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

    private void setPassword(User user, String password) {
        user.changePassword(this.encodePassword(password));
        var savedUser = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserCredentialsChangedEvent(savedUser));
    }

    @Transactional
    @Override
    public void changePassword(UserId userId, String password, String originalPassword) throws UserException {
        var user = this.getUser(userId);
        if (!this.matchesPassword(originalPassword, user.getPassword())) {
            throw new UserException(
                    message("identity.user.originalPasswordIncorrect",
                            "The original password is incorrect"));
        }
        this.setPassword(user, password);
    }

    @Transactional
    @Override
    public void resetPassword(UserId userId, String password) throws UserException {
        var user = this.getUser(userId);
        this.setPassword(user, password);
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

    @Override
    public User invokePostProcessAfterGetUserByUsername(User user) {
        return Processors.stream(processors)
                .map(UserProcessor::postProcessAfterGetUserByUsername)
                .apply(user);
    }

    @Override
    public User invokePostProcessAfterGetUserByPhone(User user) {
        return Processors.stream(processors)
                .map(UserProcessor::postProcessAfterGetUserByPhone)
                .apply(user);
    }
}
