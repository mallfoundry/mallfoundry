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

package org.mallfoundry.rest.identity;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.identity.User;
import org.mallfoundry.identity.UserRegistration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserCreateRequest implements UserRegistration {
    private String nickname;
    private String password;
    private String countryCode;
    private String phone;
    private String email;
    private Mode mode;
    private Map<String, String> parameters = new HashMap<>();

    @Override
    public void setParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public User assignToUser(User user) {
        return user.toBuilder().nickname(this.nickname).password(this.password)
                .countryCode(this.countryCode).phone(this.phone)
                .email(this.email).build();
    }
}
