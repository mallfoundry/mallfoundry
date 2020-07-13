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

import java.util.Date;
import java.util.List;

public abstract class UserSupport implements MutableUser {

    @Override
    public void changePassword(String password) {
        this.setPassword(password);
    }

    @Override
    public void immutable() {
        this.setImmutable(true);
    }

    @Override
    public void mutable() {
        this.setImmutable(false);
    }

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final UserSupport user;

        public BuilderSupport(UserSupport user) {
            this.user = user;
        }

        @Override
        public Builder username(String username) {
            this.user.setUsername(username);
            return this;
        }

        @Override
        public Builder avatar(String avatar) {
            this.user.setAvatar(avatar);
            return this;
        }

        @Override
        public Builder gender(Gender gender) {
            this.user.setGender(gender);
            return this;
        }

        @Override
        public Builder nickname(String nickname) {
            this.user.setNickname(nickname);
            return this;
        }

        @Override
        public Builder mutable() {
            this.user.mutable();
            return this;
        }

        @Override
        public Builder immutable() {
            this.user.immutable();
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
