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

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AccessTokenAuthenticationConverter implements AuthenticationConverter {

    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

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
