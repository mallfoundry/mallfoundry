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

package org.mallfoundry.security.access;

public abstract class PrincipalSupport implements MutablePrincipal {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final MutablePrincipal principal;

        public BuilderSupport(MutablePrincipal principal) {
            this.principal = principal;
        }

        @Override
        public Builder id(String id) {
            this.principal.setId(id);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.principal.setName(name);
            return this;
        }

        @Override
        public Builder type(String type) {
            this.principal.setType(type);
            return this;
        }

        @Override
        public Principal build() {
            return this.principal;
        }
    }
}
