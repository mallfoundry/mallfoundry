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

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalUserService implements UserService {

    private static final String USER_ID_VALUE_NAME = "identity.user.id";

    private final List<UserValidator> userValidators;

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public InternalUserService(List<UserValidator> userValidators,
                               ApplicationEventPublisher eventPublisher,
                               UserRepository userRepository) {
        this.userValidators = userValidators;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserId createUserId(String userId) {
        return new InternalUserId(userId);
    }

    @Override
    public User createUser(String id) {
        return new InternalUser(id);
    }

    @Transactional
    @Override
    public User createUser(UserRegistration registration) {
        if (CollectionUtils.isNotEmpty(this.userValidators)) {
            this.userValidators.forEach(userValidator -> userValidator.validateCreateUser(registration));
        }
        var user = new InternalUser(PrimaryKeyHolder.next(USER_ID_VALUE_NAME));
        registration.assignToUser(user);
        user.changePassword(this.encodePassword(registration.getPassword()));
        this.eventPublisher.publishEvent(new InternalUserCreatedEvent(user));
        this.userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        var savedUser = this.userRepository.findById(user.getId()).orElseThrow();
        if (!Objects.equals(user.getNickname(), savedUser.getNickname())) {
            savedUser.setNickname(savedUser.getNickname());
        }
        this.eventPublisher.publishEvent(new InternalUserChangedEvent(savedUser));
        return this.userRepository.save(savedUser);
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    @Override
    public void changePassword(String username, String password, String originalPassword) throws UserException {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        if (!this.matchesPassword(originalPassword, user.getPassword())) {
            throw new UserException("The original password is incorrect");
        }
        user.changePassword(this.encodePassword(password));
        this.eventPublisher.publishEvent(new InternalUserChangedEvent(user));
        this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        this.eventPublisher.publishEvent(new InternalUserDeletedEvent(user));
        this.userRepository.delete(user);
    }

    @Transactional
    public Optional<User> getUser(String userId) {
        return CastUtils.cast(this.userRepository.findById(userId));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return CastUtils.cast(this.userRepository.findByUsername(username));
    }

    @Override
    public Optional<User> getUserByMobile(String mobile) {
        return CastUtils.cast(this.userRepository.findByMobile(mobile));
    }
}
