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

import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.security.RoleService;
import org.springframework.core.annotation.Order;

import static org.mallfoundry.store.lifecycle.StoreLifecycle.POSITION_STEP;

/**
 * 商铺角色初始化。
 *
 * @author Zhi Tang
 */
@Order(POSITION_STEP * 3)
public class StoreRoleLifecycle implements StoreLifecycle {

    private final RoleService roleService;

    public StoreRoleLifecycle(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void doInitialize(Store store) {
        var storeId = store.toId();
        this.addSuperRole(storeId);
        this.addGeneralRoles(storeId);
    }

    @Override
    public void doClose(Store store) {
        this.roleService.clearRoles(store.toId());
    }

    private void addSuperRole(StoreId storeId) {
        var superRole = this.roleService.createSuperRole(storeId);
        superRole = this.roleService.addRole(superRole);
        this.roleService.changeSuperRole(superRole.toId());
    }

    private void addGeneralRoles(StoreId storeId) {

    }

    @Override
    public int getPosition() {
        return POSITION_STEP * 3;
    }
}
