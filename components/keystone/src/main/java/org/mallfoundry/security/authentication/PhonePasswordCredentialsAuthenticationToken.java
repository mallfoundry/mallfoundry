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

package org.mallfoundry.security.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@Setter
public class PhonePasswordCredentialsAuthenticationToken extends AbstractAuthenticationToken {
    private String countryCode;
    private String phone;
    private String password;

    public PhonePasswordCredentialsAuthenticationToken(String countryCode, String phone, String password) {
        super(null);
        this.countryCode = countryCode;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return this.phone;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

}
