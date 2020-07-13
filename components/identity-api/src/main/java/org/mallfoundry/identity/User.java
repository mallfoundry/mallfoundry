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

package org.mallfoundry.identity;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

public interface User extends ObjectBuilder.ToBuilder<User.Builder> {

    String getId();

    String getUsername();

    void setUsername(String username);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    Gender getGender();

    void setGender(Gender gender);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getEmail();

    void setEmail(String email);

    boolean isImmutable();

    void mutable();

    void immutable();

    String getPassword();

    void changePassword(String password);

    List<String> getAuthorities();

    void setAuthorities(List<String> authorities);

    Date getCreatedTime();

    void create();

    interface Builder extends ObjectBuilder<User> {

        Builder username(String username);

        Builder avatar(String avatar);

        Builder nickname(String nickname);

        Builder gender(Gender gender);

        Builder countryCode(String countryCode);

        Builder mobile(String mobile);

        Builder email(String mail);

        Builder mutable();

        Builder immutable();

        Builder password(String password);

        Builder authorities(List<String> authorities);
    }
}
