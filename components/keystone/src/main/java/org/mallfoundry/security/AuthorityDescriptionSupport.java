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

import java.util.List;

public abstract class AuthorityDescriptionSupport implements MutableAuthorityDescription {

    @Override
    public void addAuthority(AuthorityDescription authority) {
        this.getChildren().add(authority);
    }

    @Override
    public void addAuthorities(List<AuthorityDescription> authorities) {
        this.getChildren().addAll(authorities);
    }

    @Override
    public void removeAuthority(AuthorityDescription authority) {
        this.getChildren().remove(authority);
    }

    @Override
    public void removeAuthorities(List<AuthorityDescription> authorities) {
        this.getChildren().removeAll(authorities);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final AuthorityDescriptionSupport authority;

        protected BuilderSupport(AuthorityDescriptionSupport authority) {
            this.authority = authority;
        }

        @Override
        public Builder authority(String authority) {
            this.authority.setAuthority(authority);
            return this;
        }

        @Override
        public Builder language(String language) {
            this.authority.setLanguage(language);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.authority.setName(name);
            return this;
        }

        @Override
        public Builder addAuthority(AuthorityDescription authority) {
            this.authority.addAuthority(authority);
            return this;
        }

        @Override
        public Builder addAuthorities(List<AuthorityDescription> authorities) {
            this.authority.addAuthorities(authorities);
            return this;
        }

        @Override
        public AuthorityDescription build() {
            return this.authority;
        }
    }
}
