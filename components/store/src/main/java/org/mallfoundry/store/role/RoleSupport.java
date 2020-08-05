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

package org.mallfoundry.store.role;

import org.mallfoundry.store.staff.Staff;

import java.util.Date;
import java.util.List;

public abstract class RoleSupport implements MutableRole {

    @Override
    public void addStaff(Staff staff) {
        this.setStaffsCount(this.getStaffsCount() + 1);
    }

    @Override
    public void removeStaff(Staff staff) {
        this.setStaffsCount(this.getStaffsCount() - 1);
    }

    @Override
    public void primitive() {
        this.setType(RoleType.PRIMITIVE);
        this.setCreatedTime(new Date());
    }

    @Override
    public void predefine() {
        this.setType(RoleType.PREDEFINED);
        this.setCreatedTime(new Date());
    }

    @Override
    public void custom() {
        this.setType(RoleType.CUSTOM);
        this.setCreatedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final RoleSupport role;

        protected BuilderSupport(RoleSupport role) {
            this.role = role;
        }

        @Override
        public Builder id(String id) {
            this.role.setId(id);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.role.setName(name);
            return this;
        }

        @Override
        public Builder authorities(List<String> authorities) {
            this.role.setAuthorities(authorities);
            return this;
        }

        @Override
        public Role build() {
            return this.role;
        }
    }
}
