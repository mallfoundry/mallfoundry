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

import org.mallfoundry.security.authentication.CaptchaCredentials;
import org.mallfoundry.security.authentication.CaptchaCredentialsAuthenticationToken;
import org.mallfoundry.security.authentication.Credentials;
import org.mallfoundry.security.authentication.MobilePasswordCredentials;
import org.mallfoundry.security.authentication.MobilePasswordCredentialsAuthenticationToken;
import org.mallfoundry.security.SecurityUser;
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

        } else if (credentials instanceof MobilePasswordCredentials) {
            var mpCredentials = (MobilePasswordCredentials) credentials;
            authentication = new MobilePasswordCredentialsAuthenticationToken(
                    mpCredentials.getCountryCode(), mpCredentials.getMobile(), mpCredentials.getPassword());
        } else if (credentials instanceof CaptchaCredentials) {
            var captchaCredentials = (CaptchaCredentials) credentials;
            authentication = new CaptchaCredentialsAuthenticationToken(captchaCredentials.getToken(), captchaCredentials.getCode());
        }
        authentication = this.authenticationManager.authenticate(authentication);
        var user = (SecurityUser) authentication.getPrincipal();
        var username = user.getUsername();
        return tokenService.getAccessToken(username)
                .orElseGet(() -> this.tokenService.storeAccessToken(this.tokenService.createToken(username)));
    }
}
