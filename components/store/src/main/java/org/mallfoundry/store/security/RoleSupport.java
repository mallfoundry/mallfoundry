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

package org.mallfoundry.store.security;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.store.staff.Staff;

import java.util.Date;
import java.util.List;

import static org.mallfoundry.store.security.RoleType.CUSTOM;
import static org.mallfoundry.store.security.RoleType.PREDEFINED;
import static org.mallfoundry.store.security.RoleType.PRIMITIVE;

public abstract class RoleSupport implements MutableRole {

    @Override
    public RoleId toRoleId() {
        return new ImmutableRoleId(this.getStoreId(), this.getId());
    }

    @Override
    public void addStaff(Staff staff) {
        this.setStaffsCount(this.getStaffsCount() + 1);
    }

    @Override
    public void addStaffs(List<Staff> staffs) {
        this.setStaffsCount(this.getStaffsCount() + CollectionUtils.size(staffs));
    }

    @Override
    public void removeStaff(Staff staff) {
        this.setStaffsCount(this.getStaffsCount() - 1);
    }

    @Override
    public void removeStaffs(List<Staff> staffs) {
        this.setStaffsCount(this.getStaffsCount() - CollectionUtils.size(staffs));
    }

    @Override
    public void clearStaffs() {
        this.setStaffsCount(0);
    }

    @Override
    public boolean isPrimitive() {
        return PRIMITIVE == this.getType();
    }

    @Override
    public boolean isPredefined() {
        return PREDEFINED == this.getType();
    }

    @Override
    public boolean isCustom() {
        return CUSTOM == this.getType();
    }

    @Override
    public void primitive() {
        this.setType(PRIMITIVE);
        this.setCreatedTime(new Date());
    }

    @Override
    public void predefine() {
        this.setType(PREDEFINED);
        this.setCreatedTime(new Date());
    }

    @Override
    public void custom() {
        this.setType(CUSTOM);
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
        public Builder description(String description) {
            this.role.setDescription(description);
            return this;
        }

        @Override
        public Builder primitive() {
            this.role.setType(PRIMITIVE);
            return this;
        }

        @Override
        public Builder predefine() {
            this.role.setType(PREDEFINED);
            return this;
        }

        @Override
        public Builder custom() {
            this.role.setType(CUSTOM);
            return this;
        }

        @Override
        public Role build() {
            return this.role;
        }
    }
}
