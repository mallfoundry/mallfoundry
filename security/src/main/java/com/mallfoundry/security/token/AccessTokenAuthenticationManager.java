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

import com.mallfoundry.access.token.AccessTokenService;
import com.mallfoundry.access.token.AccessToken;
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

    public AccessToken authenticate(String username, String password) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        this.authenticationManager.authenticate(authentication);
        return tokenService
                .getAccessToken(username)
                .orElseGet(() -> this.tokenService.storeAccessToken(this.tokenService.createToken(username)));
    }

}
