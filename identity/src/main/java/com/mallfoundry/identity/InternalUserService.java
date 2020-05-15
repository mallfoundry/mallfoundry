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

package com.mallfoundry.identity;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InternalUserService implements UserService {

    private static final String USER_ID_VALUE_NAME = "identity.user.id";

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public InternalUserService(ApplicationEventPublisher eventPublisher, UserRepository userRepository) {
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Transactional
    @Override
    public User createUser(String username, String password) {
        return new InternalUser(PrimaryKeyHolder.next(USER_ID_VALUE_NAME), username, password);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return this.userRepository.save(InternalUser.of(user));
    }

    @Transactional
    @Override
    public void changePassword(String username, String password, String originalPassword) throws UserException {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        if (!this.passwordEncoder.matches(originalPassword, user.getPassword())) {
            throw new UserException("The original password is incorrect");
        }
        user.changePassword(this.passwordEncoder.encode(password));
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        var user = this.userRepository.findByUsername(username).orElseThrow();
        this.eventPublisher.publishEvent(new InternalUserDeletedEvent(user));
        this.userRepository.delete(user);
    }

    @Cacheable(value = "Users", key = "#username")
    @Transactional
    public Optional<User> getUser(String username) {
        return CastUtils.cast(this.userRepository.findByUsername(username));
    }
}
