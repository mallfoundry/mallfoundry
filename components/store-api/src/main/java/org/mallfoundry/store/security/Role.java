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

import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;

/**
 * 店铺角色对象。
 *
 * @author Zhi Tang
 */
public interface Role extends StoreOwnership, ObjectBuilder.ToBuilder<Role.Builder> {

    RoleId toId();

    String getId();

    void setId(String id);

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    List<String> getAuthorities();

    void setAuthorities(List<String> authorities);

    String getDescription();

    void setDescription(String description);

    RoleType getType();

    int getStaffsCount();

    void addStaff(Staff staff);

    void addStaffs(List<Staff> staffs);

    void removeStaff(Staff staff);

    void removeStaffs(List<Staff> staffs);

    void clearStaffs();

    boolean isPrimitive();

    boolean isPredefined();

    boolean isCustom();

    void primitive();

    void predefine();

    void custom();

    Date getCreatedTime();

    interface Builder extends ObjectBuilder<Role> {

        Builder tenantId(String tenantId);

        Builder storeId(String storeId);

        Builder id(String id);

        Builder name(String name);

        Builder description(String description);

        Builder authorities(List<String> authorities);

        Builder primitive();

        Builder predefine();

        Builder custom();
    }
}
