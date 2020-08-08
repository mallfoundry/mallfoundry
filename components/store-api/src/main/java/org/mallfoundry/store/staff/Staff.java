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

import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.store.security.Role;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

public interface Staff extends StoreOwnership, ObjectBuilder.ToBuilder<Staff.Builder> {

    String getId();

    void setId(String id);

    void setStoreId(String storeId);

    String getNumber();

    void setNumber(String number);

    String getName();

    void setName(String name);

    String getAvatar();

    void setAvatar(String avatar);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getPhone();

    void setPhone(String phone);

    List<Role> getRoles();

    void setRoles(List<Role> roles);

    void addRole(Role role);

    void removeRole(Role role);

    void addRoles(List<Role> roles);

    void removeRoles(List<Role> roles);

    Date getCreatedTime();

    void create();

    interface Builder extends ObjectBuilder<Staff> {

        Builder id(String id);

        Builder number(String number);

        Builder name(String name);

        Builder avatar(String avatar);

        Builder countryCode(String countryCode);

        Builder phone(String phone);
    }
}
