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

package org.mallfoundry.security;

import org.mallfoundry.util.Positions;

import java.util.List;

public abstract class AuthoritySupport implements MutableAuthority {

    @Override
    public void addAuthority(Authority authority) {
        authority.setPosition(Integer.MAX_VALUE);
        this.getChildren().add(authority);
        Positions.sort(this.getChildren());
    }

    @Override
    public void addAuthorities(List<Authority> authorities) {
        authorities.forEach(authority -> authority.setPosition(Integer.MAX_VALUE));
        this.getChildren().addAll(authorities);
        Positions.sort(this.getChildren());
    }

    @Override
    public void removeAuthority(Authority authority) {
        this.getChildren().remove(authority);
    }

    @Override
    public void removeAuthorities(List<Authority> authorities) {
        this.getChildren().removeAll(authorities);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final AuthoritySupport authority;

        protected BuilderSupport(AuthoritySupport authority) {
            this.authority = authority;
        }

        @Override
        public Builder code(String code) {
            this.authority.setCode(code);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.authority.setName(name);
            return this;
        }

        @Override
        public Builder position(int position) {
            this.authority.setPosition(position);
            return this;
        }

        @Override
        public Builder addAuthority(Authority authority) {
            this.authority.addAuthority(authority);
            return this;
        }

        @Override
        public Builder addAuthorities(List<Authority> authorities) {
            this.authority.addAuthorities(authorities);
            return this;
        }

        @Override
        public Authority build() {
            return this.authority;
        }
    }
}
