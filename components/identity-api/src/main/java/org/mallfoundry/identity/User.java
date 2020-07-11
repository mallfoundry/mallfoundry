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

public interface User {

    String getId();

    String getUsername();

    void setUsername(String username);

    String getAvatar();

    void setAvatar(String avatar);

    String getNickname();

    void setNickname(String nickname);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getMobile();

    void setMobile(String mobile);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void changePassword(String password);

    List<String> getAuthorities();

    void setAuthorities(List<String> authorities);

    Date getCreatedTime();

    void create();

    default Builder toBuilder() {
        return new BuilderSupport(this);
    }

    interface Builder extends ObjectBuilder<User> {

        Builder username(String username);

        Builder nickname(String nickname);

        Builder countryCode(String countryCode);

        Builder mobile(String mobile);

        Builder email(String mail);

        Builder password(String password);

        Builder authorities(List<String> authorities);
    }

    class BuilderSupport implements Builder {
        private final User user;

        public BuilderSupport(User user) {
            this.user = user;
        }

        @Override
        public Builder username(String username) {
            this.user.setUsername(username);
            return this;
        }

        @Override
        public Builder nickname(String nickname) {
            this.user.setNickname(nickname);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.user.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder mobile(String mobile) {
            this.user.setMobile(mobile);
            return this;
        }

        @Override
        public Builder email(String email) {
            this.user.setEmail(email);
            return this;
        }

        @Override
        public Builder password(String password) {
            this.user.changePassword(password);
            return this;
        }

        @Override
        public Builder authorities(List<String> authorities) {
            this.user.setAuthorities(authorities);
            return this;
        }

        @Override
        public User build() {
            return this.user;
        }
    }
}
