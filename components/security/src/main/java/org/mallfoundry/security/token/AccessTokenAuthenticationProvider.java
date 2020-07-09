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

package org.mallfoundry.security.token;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.UserSubject;
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
        var securityUser = new UserSubject(user);
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
