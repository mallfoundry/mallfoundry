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

package org.mallfoundry.security.token.providers;

import org.mallfoundry.identity.UserSearch;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.CryptoUtils;
import org.mallfoundry.security.authentication.Credentials;
import org.mallfoundry.security.authentication.UsernamePasswordCredentials;
import org.mallfoundry.security.token.AccessTokenId;
import org.mallfoundry.security.token.AccessTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordCredentialsAccessTokenProvider implements AccessTokenProvider {

    private final UserService userService;

    public UsernamePasswordCredentialsAccessTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AccessTokenId authenticate(Credentials credentials) throws AuthenticationException {
        var upCredentials = (UsernamePasswordCredentials) credentials;
        String username = upCredentials.getUsername();
        var user = this.userService
                .findUser(new UserSearch() {
                    @Override
                    public String getUsername() {
                        return username;
                    }
                })
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s not found", username)));
        this.authenticationChecks(upCredentials.getPassword(), user.getPassword());
        return new AccessTokenId(user.getUsername());
    }

    @Override
    public boolean supports(Credentials credentials) {
        return credentials instanceof UsernamePasswordCredentials;
    }

    protected void authenticationChecks(String rawPassword, String encodedPassword) throws AuthenticationException {
        if (!CryptoUtils.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
