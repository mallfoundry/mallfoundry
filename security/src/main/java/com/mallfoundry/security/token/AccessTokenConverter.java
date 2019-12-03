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


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public abstract class AccessTokenConverter {

    private static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";
    private static final String TOKEN_NAME = "token";


    public static String convert(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(token)) {
            token = token.trim();
            if (!StringUtils.startsWithIgnoreCase(token, AUTHENTICATION_SCHEME_BEARER)) {
                return null;
            }
            token = StringUtils.substring(token, 6);
        }

        if (Objects.isNull(token)) {
            token = request.getParameter(TOKEN_NAME);
        }
        return StringUtils.trim(token);
    }
}
