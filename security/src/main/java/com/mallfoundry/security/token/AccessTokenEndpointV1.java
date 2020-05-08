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

import com.mallfoundry.http.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/v1/access")
public class AccessTokenEndpointV1 {

    private final AccessTokenAuthenticationManager tokenAuthenticationManager;

    private final AccessTokenService tokenService;

    public AccessTokenEndpointV1(AccessTokenAuthenticationManager tokenAuthenticationManager,
                                 AccessTokenService tokenService) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(String username, String password) {
        try {
            AccessToken token = this.tokenAuthenticationManager.authenticate(username, password);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorMessage.error("invalid_grant", e.getMessage()));
        }
    }

    @DeleteMapping("/token")
    public void deleteToken(HttpServletRequest request) {
        String token = AccessTokenConverter.convert(request);
        this.tokenService.deleteAccessToken(token);
    }
}
