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

package com.mallfoundry.security;

import com.mallfoundry.identity.application.UserService;
import com.mallfoundry.identity.domain.Authority;
import com.mallfoundry.identity.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Load security user service.
 */
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserService userService;

    public SecurityUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUser(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        List<Authority> authorities = this.userService.getAuthorities(user.getId());
        return new SecurityUser(user, authorities);
    }
}
