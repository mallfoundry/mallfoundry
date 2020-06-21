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
import org.apache.commons.lang3.StringUtils;
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

    private final UsernameGenerator usernameGenerator;

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public InternalUserService(List<UserValidator> userValidators,
                               UsernameGenerator usernameGenerator,
                               ApplicationEventPublisher eventPublisher,
                               UserRepository userRepository) {
        this.userValidators = userValidators;
        this.usernameGenerator = usernameGenerator;
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
        if (StringUtils.isBlank(user.getUsername())) {
            user.setUsername(this.usernameGenerator.generate(user.getId()));
        }
        user.changePassword(this.encodePassword(registration.getPassword()));
        this.eventPublisher.publishEvent(new InternalUserCreatedEvent(user));
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public User setUser(User user) {
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

    private void setPassword(InternalUser user, String password) {
        user.changePassword(this.encodePassword(password));
        var savedUser = this.userRepository.save(user);
        this.eventPublisher.publishEvent(new InternalUserPasswordChangedEvent(savedUser));
    }

    @Transactional
    @Override
    public void changePassword(String username, String password, String originalPassword) throws UserException {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        if (!this.matchesPassword(originalPassword, user.getPassword())) {
            throw new UserException("The original password is incorrect");
        }
        this.setPassword(user, password);
    }

    @Transactional
    @Override
    public void resetPassword(String username, String password) throws UserException {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        this.setPassword(user, password);
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
    public Optional<User> getUserByMobile(String countryCode, String mobile) {
        return CastUtils.cast(this.userRepository.findByMobile(mobile));
    }
}
