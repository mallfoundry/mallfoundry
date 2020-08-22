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

package org.mallfoundry.store;

import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.Resource;
import org.mallfoundry.store.blob.StoreBlobService;
import org.mallfoundry.store.member.MemberService;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.StaffService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class StoreClosedBasicCleanupEventListener {
    private final AccessControlManager accessControlManager;
    private final StoreAddressService storeAddressService;
    private final StaffService staffService;
    private final MemberService memberService;
    private final RoleService roleService;
    private final StoreBlobService storeBlobService;

    public StoreClosedBasicCleanupEventListener(AccessControlManager accessControlManager,
                                                StoreAddressService storeAddressService,
                                                StaffService staffService,
                                                MemberService memberService,
                                                RoleService roleService,
                                                StoreBlobService storeBlobService) {
        this.accessControlManager = accessControlManager;
        this.storeAddressService = storeAddressService;
        this.staffService = staffService;
        this.memberService = memberService;
        this.roleService = roleService;
        this.storeBlobService = storeBlobService;
    }

    @EventListener
    public void onStoreClosed(StoreClosedEvent event) {
        var store = event.getStore();
        var storeId = store.toId();
        this.deleteResource(storeId);
        this.storeAddressService.clearStoreAddresses(storeId);
        this.staffService.clearStaffs(storeId);
        this.roleService.clearRoles(storeId);
        this.memberService.clearMembers(storeId);
        this.storeBlobService.clearStoreBlobs(storeId);
    }

    private void deleteResource(StoreId storeId) {
        var resource = this.accessControlManager.createResource(Resource.STORE_TYPE, storeId.getId());
        this.accessControlManager.removeResource(resource);
    }
}
