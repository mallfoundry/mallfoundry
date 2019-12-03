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

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AccessTokenAuthenticationConverter implements AuthenticationConverter {

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    public AccessTokenAuthenticationConverter() {
        this(new WebAuthenticationDetailsSource());
    }


    public AccessTokenAuthenticationConverter(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public AccessTokenAuthentication convert(HttpServletRequest request) {
        String token = AccessTokenConverter.convert(request);

        if (Objects.isNull(token)) {
            return null;
        }

        AccessTokenAuthentication result = new AccessTokenAuthentication(token);
        result.setDetails(authenticationDetailsSource.buildDetails(request));
        return result;
    }
}
