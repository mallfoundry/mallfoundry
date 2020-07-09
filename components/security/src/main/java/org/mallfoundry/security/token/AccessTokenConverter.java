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
