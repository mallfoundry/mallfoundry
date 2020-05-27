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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.http.ErrorMessage;
import org.mallfoundry.security.Credentials;
import org.mallfoundry.security.DefaultCaptchaCredentials;
import org.mallfoundry.security.DefaultPasswordCredentials;
import org.mallfoundry.security.GrantType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/v1")
public class AccessTokenEndpointV1 {

    private final AccessTokenAuthenticationManager tokenAuthenticationManager;

    private final AccessTokenService tokenService;

    public AccessTokenEndpointV1(AccessTokenAuthenticationManager tokenAuthenticationManager,
                                 AccessTokenService tokenService) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestParam(name = "grant_type", required = false) String grantType,
                                   HttpServletRequest request) {
        var type = GrantType.valueOf(StringUtils.upperCase(grantType));
        Credentials credentials = null;
        if (type == GrantType.PASSWORD) {
            credentials = new DefaultPasswordCredentials(request.getParameter("username"), request.getParameter("password"));
        } else if (type == GrantType.CAPTCHA) {
            credentials = new DefaultCaptchaCredentials(request.getParameter("token"), request.getParameter("code"));
        }
        try {
            var token = this.tokenAuthenticationManager.authenticate(credentials);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.error("invalid_grant", e.getMessage()));
        }
    }

    @DeleteMapping("/token")
    public void deleteToken(HttpServletRequest request) {
        String token = AccessTokenConverter.convert(request);
        this.tokenService.deleteAccessToken(token);
    }
}
