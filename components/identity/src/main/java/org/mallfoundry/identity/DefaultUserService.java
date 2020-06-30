/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.identity;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mallfoundry.i18n.MessageHolder.message;

@Service
public class DefaultUserService implements UserService {

    private static final String USER_ID_VALUE_NAME = "identity.user.id";

    private final UserConfiguration userConfiguration;

    private final List<UserValidator> userValidators;

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public DefaultUserService(UserConfiguration userConfiguration,
                              List<UserValidator> userValidators,
                              ApplicationEventPublisher eventPublisher,
                              UserRepository userRepository) {
        this.userConfiguration = userConfiguration;
        this.userValidators = ListUtils.emptyIfNull(userValidators);
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserId createUserId(String userId) {
        return new ImmutableUserId(userId);
    }

    @Override
    public User createUser(String id) {
        return new InternalUser(id);
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
        var user = registration.assignToUser(this.userRepository.create(PrimaryKeyHolder.next(USER_ID_VALUE_NAME)));
        this.setDefaultUsername(user);
        this.setDefaultNickname(user);
        user.changePassword(this.encodePassword(registration.getPassword()));
        user.create();
        var savedUser = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserCreatedEvent(savedUser));
        return savedUser;
    }

    private User getNonUser(String id) {
        return this.userRepository.findById(id).orElseThrow(() -> UserExceptions.notExists(id));
    }

    @Transactional
    @Override
    public User setUser(User user) {
        var savedUser = this.getNonUser(user.getId());
        if (!Objects.equals(user.getNickname(), savedUser.getNickname())) {
            savedUser.setNickname(savedUser.getNickname());
        }
        this.eventPublisher.publishEvent(new ImmutableUserChangedEvent(savedUser));
        return this.userRepository.save(savedUser);
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private void setPassword(User user, String password) {
        user.changePassword(this.encodePassword(password));
        var savedUser = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new ImmutableUserCredentialsChangedEvent(savedUser));
    }

    @Transactional
    @Override
    public void changePassword(String id, String password, String originalPassword) throws UserException {
        var user = this.getNonUser(id);
        if (!this.matchesPassword(originalPassword, user.getPassword())) {
            throw new UserException(
                    message("identity.user.originalPasswordIncorrect",
                            "The original password is incorrect"));
        }
        this.setPassword(user, password);
    }

    @Transactional
    @Override
    public void resetPassword(String id, String password) throws UserException {
        var user = this.getNonUser(id);
        this.setPassword(user, password);
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        var user = this.getNonUser(id);
        this.eventPublisher.publishEvent(new ImmutableUserDeletedEvent(user));
        this.userRepository.delete(user);
    }

    @Transactional
    public Optional<User> getUser(String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByMobile(String countryCode, String mobile) {
        return this.userRepository.findByMobile(mobile);
    }
}
