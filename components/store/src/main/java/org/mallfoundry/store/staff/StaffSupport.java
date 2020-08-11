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

package org.mallfoundry.store.staff;

import org.mallfoundry.store.security.Role;

import java.util.Date;
import java.util.List;

public abstract class StaffSupport implements MutableStaff {

    @Override
    public void addRole(Role role) {
        this.getRoles().add(role);
    }

    @Override
    public void removeRole(Role role) {
        this.getRoles().remove(role);
    }

    @Override
    public void addRoles(List<Role> roles) {
        this.getRoles().addAll(roles);
    }

    @Override
    public void removeRoles(List<Role> roles) {
        this.getRoles().removeAll(roles);
    }

    @Override
    public void create() {
        this.active();
        this.setCreatedTime(new Date());
    }

    @Override
    public void active() {
        this.setStatus(StaffStatus.ACTIVE);
    }

    @Override
    public void inactive() {
        this.setStatus(StaffStatus.INACTIVE);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final StaffSupport staff;

        protected BuilderSupport(StaffSupport staff) {
            this.staff = staff;
        }

        @Override
        public Builder id(String id) {
            this.staff.setId(id);
            return this;
        }

        @Override
        public Builder type(StaffType type) {
            this.staff.setType(type);
            return this;
        }

        @Override
        public Builder active() {
            this.staff.active();
            return this;
        }

        @Override
        public Builder inactive() {
            this.staff.inactive();
            return this;
        }

        @Override
        public Builder number(String number) {
            this.staff.setNumber(number);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.staff.setName(name);
            return this;
        }

        @Override
        public Builder avatar(String avatar) {
            this.staff.setAvatar(avatar);
            return this;
        }

        @Override
        public Builder countryCode(String countryCode) {
            this.staff.setCountryCode(countryCode);
            return this;
        }

        @Override
        public Builder phone(String phone) {
            this.staff.setPhone(phone);
            return this;
        }

        @Override
        public Staff build() {
            return this.staff;
        }
    }
}
