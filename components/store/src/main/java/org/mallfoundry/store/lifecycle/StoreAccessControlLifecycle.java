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

import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.Resource;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;
import org.springframework.core.annotation.Order;

import static org.mallfoundry.store.lifecycle.StoreLifecycle.POSITION_STEP;


@Order(POSITION_STEP * 2)
public class StoreAccessControlLifecycle implements StoreLifecycle {

    private final AccessControlManager manager;

    public StoreAccessControlLifecycle(AccessControlManager manager) {
        this.manager = manager;
    }

    @Override
    public void doInitialize(Store store) {
        var tenantResource = this.manager.createResource(Resource.TENANT_TYPE, store.getTenantId());
        var resource = this.manager.createResource(Resource.STORE_TYPE, store.getId());
        var owner = this.manager.createPrincipal(Principal.USER_TYPE, store.getOwnerId());
        // Create AccessControl by parent access control object.
        var accessControl = this.manager.getAccessControl(tenantResource).createAccessControl(owner, resource);
        this.manager.deleteAccessControl(accessControl);
        this.manager.addAccessControl(accessControl);
    }

    @Override
    public void doClose(Store store) {
        this.deleteResource(store.toId());
    }
    private void deleteResource(StoreId storeId) {
        var resource = this.manager.createResource(Resource.STORE_TYPE, storeId.getId());
        this.manager.removeResource(resource);
    }
    @Override
    public int getPosition() {
        return POSITION_STEP * 2;
    }
}
