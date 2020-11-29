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
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class UserResponse {

    private String id;

    private String username;

    private String nickname;

    private String countryCode;

    private String phone;

    private String avatar;

    private String email;

    private Collection<String> authorities;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.countryCode = user.getCountryCode();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.authorities = CollectionUtils.isEmpty(user.getAuthorities())
                ? Collections.emptyList()
                : Collections.unmodifiableCollection(user.getAuthorities());
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}
