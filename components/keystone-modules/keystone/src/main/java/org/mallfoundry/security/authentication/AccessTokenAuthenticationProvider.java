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

package org.mallfoundry.security.authentication;

import org.mallfoundry.identity.UserId;
import org.mallfoundry.identity.UserSearch;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.UserAuthoritiesEnhancer;
import org.mallfoundry.security.UserDetailsSubject;
import org.mallfoundry.security.token.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final List<UserAuthoritiesEnhancer> enhancers;

    private final AccessTokenService tokenService;

    private final UserService userService;

    public AccessTokenAuthenticationProvider(@Autowired(required = false)
                                             @Lazy List<UserAuthoritiesEnhancer> enhancers,
                                             AccessTokenService tokenService,
                                             UserService userService) {
        this.enhancers = enhancers;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessTokenAuthentication tokenAuthentication = (AccessTokenAuthentication) authentication;
        String username =
                this.tokenService
                        .readAccessToken(tokenAuthentication.getName())
                        .orElseThrow(() -> new BadCredentialsException("Bad credentials"))
                        .getUsername();
        var user = this.userService
                .findUser(new UserSearch() {
                    @Override
                    public String getUsername() {
                        return username;
                    }
                })
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        var enhancedAuthorities = this.getEnhancedAuthorities(user.toId());
        var securityUser = new UserDetailsSubject(user, enhancedAuthorities);
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }


    private Collection<String> getEnhancedAuthorities(UserId userId) {
        return this.enhancers.stream().map(enhancer -> enhancer.getAuthorities(userId))
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
