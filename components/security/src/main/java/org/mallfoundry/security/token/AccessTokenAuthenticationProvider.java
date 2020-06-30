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

package org.mallfoundry.security.token;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.DefaultSecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessTokenAuthentication tokenAuthentication = (AccessTokenAuthentication) authentication;
        String username =
                this.tokenService
                        .readAccessToken(tokenAuthentication.getName())
                        .orElseThrow(() -> new BadCredentialsException("Bad credentials"))
                        .getUsername();
        var user = this.userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        var securityUser = new DefaultSecurityUser(user);
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
