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

import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.AuthorizeHolder;
import org.mallfoundry.security.access.Resource;
import org.mallfoundry.store.StoreService;
import org.mallfoundry.store.staff.Staff;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Objects;

/**
 * 店铺角色对象鉴权者。
 *
 * @author Zhi Tang
 */
public class RoleAuthorizeProcessor implements RoleProcessor {

    private final StoreService storeService;

    public RoleAuthorizeProcessor(StoreService storeService) {
        this.storeService = storeService;
    }

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_ROLE_ADD + ","
            + AllAuthorities.STORE_ROLE_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Role preProcessBeforeAddRole(Role role) {
        return role;
    }

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_ROLE_WRITE + ","
            + AllAuthorities.STORE_ROLE_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Role preProcessBeforeUpdateRole(Role role) {
        return role;
    }

    @PostAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#role.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_ROLE_DELETE + ","
            + AllAuthorities.STORE_ROLE_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Role preProcessBeforeDeleteRole(Role role) {
        if (role.isPrimitive()) {
            AuthorizeHolder.authorize("false");
        } else if (role.isPredefined()) {
            var store = this.storeService.getStore(
                    this.storeService.createStoreId(role.getTenantId(), role.getStoreId()));
            if (!Objects.equals(store.getOwnerId(), SubjectHolder.getSubject().getId())) {
                AuthorizeHolder.authorize(
                        "hasAuthority('" + AllAuthorities.SUPER_ADMIN + "') or hasAuthority('" + AllAuthorities.ADMIN + "')");
            }
        }
        return role;
    }

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_WRITE + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_ROLE_WRITE + ","
            + AllAuthorities.STORE_ROLE_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeAddRoleStaff(Role role, Staff staff) {
        return staff;
    }

    @PreAuthorize("hasAuthority('" + AllAuthorities.SUPER_ADMIN + "')"
            + " or hasAuthority('" + AllAuthorities.ADMIN + "') "
            + " or hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_WRITE + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_ROLE_WRITE + ","
            + AllAuthorities.STORE_ROLE_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeRemoveRoleStaff(Role role, Staff staff) {
        return staff;
    }
}
