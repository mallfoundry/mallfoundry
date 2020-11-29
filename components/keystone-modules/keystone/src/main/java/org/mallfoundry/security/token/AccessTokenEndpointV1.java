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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.http.ErrorMessage;
import org.mallfoundry.security.authentication.Credentials;
import org.mallfoundry.security.authentication.CredentialsType;
import org.mallfoundry.security.authentication.DefaultCaptchaCredentials;
import org.mallfoundry.security.authentication.DefaultPhonePasswordCredentials;
import org.mallfoundry.security.authentication.DefaultUsernamePasswordCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@RestController
@RequestMapping("/v1")
public class AccessTokenEndpointV1 {

    private final AccessTokenManager accessTokenManager;

    public AccessTokenEndpointV1(AccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    @PostMapping("/token")
    public ResponseEntity<?> createAccessToken(@RequestParam(name = "grant_type", required = false) String grantType,
                                               @RequestParam(name = "credentials_type", required = false) String credentialsType,
                                               HttpServletRequest request) {
        var type = CredentialsType.valueOf(StringUtils.upperCase(Objects.requireNonNullElse(credentialsType, grantType)));
        Credentials credentials = null;
        if (type == CredentialsType.USERNAME_PASSWORD) {
            credentials = new DefaultUsernamePasswordCredentials(request.getParameter("username"), request.getParameter("password"));
        } else if (type == CredentialsType.PHONE_PASSWORD) {
            credentials = new DefaultPhonePasswordCredentials(
                    request.getParameter("country_code"),
                    request.getParameter("phone"),
                    request.getParameter("password"));
        } else if (type == CredentialsType.CAPTCHA) {
            credentials = new DefaultCaptchaCredentials(request.getParameter("captcha_token"), request.getParameter("captcha_code"));
        }
        try {
            var token = this.accessTokenManager.createAccessToken(credentials);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.error("invalid_grant", e.getMessage()));
        }
    }

    @DeleteMapping("/token")
    public void deleteToken(HttpServletRequest request) {
        String token = AccessTokenConverter.convert(request);
        this.accessTokenManager.deleteAccessToken(token);
    }
}
