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

import org.mallfoundry.security.access.Resource;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.store.staff.StaffAuthorities;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 店铺角色对象鉴权者。
 *
 * @author Zhi Tang
 */
public class RoleAuthorizeProcessor implements RoleProcessor {

    @PreAuthorize("hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + RoleAuthorities.STORE_ROLE_MANAGE + "')")
    @Override
    public Role preProcessBeforeAddRole(Role role) {
        return role;
    }

    @PreAuthorize("hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + RoleAuthorities.STORE_ROLE_WRITE + "," + RoleAuthorities.STORE_ROLE_MANAGE + "')")
    @Override
    public Role preProcessBeforeUpdateRole(Role role) {
        return role;
    }

    @PreAuthorize("hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + RoleAuthorities.STORE_ROLE_DELETE + "," + RoleAuthorities.STORE_ROLE_MANAGE + "')")
    @Override
    public Role preProcessBeforeDeleteRole(Role role) {
        return role;
    }

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + StaffAuthorities.STORE_STAFF_WRITE + "," + StaffAuthorities.STORE_STAFF_MANAGE + ","
            + RoleAuthorities.STORE_ROLE_WRITE + "," + RoleAuthorities.STORE_ROLE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeAddRoleStaff(Role role, Staff staff) {
        return staff;
    }

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + StaffAuthorities.STORE_STAFF_WRITE + "," + StaffAuthorities.STORE_STAFF_MANAGE + ","
            + RoleAuthorities.STORE_ROLE_WRITE + "," + RoleAuthorities.STORE_ROLE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeRemoveRoleStaff(Role role, Staff staff) {
        return staff;
    }
}
