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

package com.mallfoundry.security.token;

import com.mallfoundry.access.application.token.AccessTokenService;
import com.mallfoundry.access.domain.token.AccessToken;
import com.mallfoundry.identity.application.UserService;
import com.mallfoundry.identity.domain.Authority;
import com.mallfoundry.identity.domain.User;
import com.mallfoundry.security.SecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final AccessTokenService tokenService;

    private final UserService userService;

    public AccessTokenAuthenticationProvider(AccessTokenService tokenService,
                                             UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessTokenAuthentication tokenAuthentication = (AccessTokenAuthentication) authentication;
        AccessToken token = tokenService.readAccessToken(tokenAuthentication.getName());

        if (Objects.isNull(token)) {
            throw new BadCredentialsException("Bad credentials");
        }

        String username = token.getUsername();
        User user = this.userService.getUser(username);
        List<Authority> authorities = this.userService.getAuthorities(username);
        SecurityUser securityUser = new SecurityUser(user, authorities);

        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
