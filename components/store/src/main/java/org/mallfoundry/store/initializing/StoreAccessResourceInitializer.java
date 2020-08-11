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

package org.mallfoundry.store.initializing;

import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.Resource;
import org.mallfoundry.store.Store;
import org.springframework.core.annotation.Order;

import static org.mallfoundry.store.initializing.StoreInitializer.POSITION_STEP;

@Order(POSITION_STEP * 2)
public class StoreAccessResourceInitializer implements StoreInitializer {

    private final AccessControlManager manager;

    public StoreAccessResourceInitializer(AccessControlManager manager) {
        this.manager = manager;
    }

    @Override
    public void doInitialize(Store store) {
        var resource = this.manager.createResource(Resource.STORE_TYPE, store.getId());
        this.manager.removeResource(resource);
        resource = this.manager.addResource(resource);
        var owner = this.manager.createPrincipal(Principal.USER_TYPE, store.getOwnerId());
        owner = this.manager.addPrincipal(owner);
        var accessControl = this.manager.createAccessControl(owner, resource);
        this.manager.addAccessControl(accessControl);
    }
}
