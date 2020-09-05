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

package org.mallfoundry.store.lifecycle;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.StaffService;
import org.mallfoundry.store.staff.StaffType;
import org.springframework.core.annotation.Order;

import static org.mallfoundry.store.lifecycle.StoreLifecycle.POSITION_STEP;

@Order(POSITION_STEP * 4)
public class StoreStaffLifecycle implements StoreLifecycle {

    private final UserService userService;

    private final StaffService staffService;

    private final RoleService roleService;

    public StoreStaffLifecycle(UserService userService,
                               StaffService staffService,
                               RoleService roleService) {
        this.userService = userService;
        this.staffService = staffService;
        this.roleService = roleService;
    }

    @Override
    public void doInitialize(Store store) {
        var storeId = store.toId();
        this.addStaffs(storeId, store.getOwnerId());
    }

    @Override
    public void doClose(Store store) {
        this.staffService.clearStaffs(store.toId());
    }

    private void addStaffs(StoreId storeId, String ownerId) {
        var userId = userService.createUserId(storeId.getTenantId(), ownerId);
        var user = userService.getUser(userId);
        var staffId = this.staffService.createStaffId(storeId, ownerId);
        var staff = this.staffService.createStaff(staffId).toBuilder()
                .type(StaffType.OWNER).active() // owner, active
                .countryCode(user.getCountryCode()).phone(user.getPhone())
                .name(user.getNickname()).avatar(user.getAvatar())
                .build();
        // 添加超级管理员角色。
        staff.addRole(this.roleService.getSuperRole(storeId));
        this.staffService.addStaff(staff);
    }

    @Override
    public int getPosition() {
        return POSITION_STEP * 4;
    }
}
