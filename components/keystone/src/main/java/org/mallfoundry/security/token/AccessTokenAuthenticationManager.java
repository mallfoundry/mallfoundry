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

import org.mallfoundry.security.authentication.CaptchaCredentials;
import org.mallfoundry.security.authentication.CaptchaCredentialsAuthenticationToken;
import org.mallfoundry.security.authentication.Credentials;
import org.mallfoundry.security.authentication.PhonePasswordCredentials;
import org.mallfoundry.security.authentication.PhonePasswordCredentialsAuthenticationToken;
import org.mallfoundry.security.Subject;
import org.mallfoundry.security.authentication.UsernamePasswordCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenAuthenticationManager {

    private final AuthenticationManager authenticationManager;

    private final AccessTokenService tokenService;

    public AccessTokenAuthenticationManager(AuthenticationManager authenticationManager,
                                            AccessTokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public AccessToken authenticate(Credentials credentials) throws AuthenticationException {
        Authentication authentication = null;
        if (credentials instanceof UsernamePasswordCredentials) {
            var passwordCredentials = (UsernamePasswordCredentials) credentials;
            authentication = new UsernamePasswordAuthenticationToken(passwordCredentials.getUsername(), passwordCredentials.getPassword());

        } else if (credentials instanceof PhonePasswordCredentials) {
            var mpCredentials = (PhonePasswordCredentials) credentials;
            authentication = new PhonePasswordCredentialsAuthenticationToken(
                    mpCredentials.getCountryCode(), mpCredentials.getPhone(), mpCredentials.getPassword());
        } else if (credentials instanceof CaptchaCredentials) {
            var captchaCredentials = (CaptchaCredentials) credentials;
            authentication = new CaptchaCredentialsAuthenticationToken(captchaCredentials.getToken(), captchaCredentials.getCode());
        }
        authentication = this.authenticationManager.authenticate(authentication);
        var user = (Subject) authentication.getPrincipal();
        var username = user.getUsername();
        return tokenService.getAccessToken(username)
                .orElseGet(() -> this.tokenService.storeAccessToken(this.tokenService.createToken(username)));
    }
}
