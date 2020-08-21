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

package org.mallfoundry.autoconfigure.store;

import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.store.StoreService;
import org.mallfoundry.store.blob.StoreBlobService;
import org.mallfoundry.store.initializing.DelegatingStoreInitializer;
import org.mallfoundry.store.initializing.StoreAccessResourceInitializer;
import org.mallfoundry.store.initializing.StoreAsyncInitializingExecutor;
import org.mallfoundry.store.initializing.StoreAsyncInitializingManager;
import org.mallfoundry.store.initializing.StoreBlobInitializer;
import org.mallfoundry.store.initializing.StoreConfigurationInitializer;
import org.mallfoundry.store.initializing.StoreInitializer;
import org.mallfoundry.store.initializing.StoreRolesInitializer;
import org.mallfoundry.store.initializing.StoreStaffsInitializer;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class StoreInitializingConfiguration {

    @Bean
    public StoreConfigurationInitializer storeConfigurationInitializer() {
        return new StoreConfigurationInitializer();
    }

    @Bean
    public DelegatingStoreInitializer delegatingStoreInitializer(@Autowired(required = false)
                                                                 @Lazy List<StoreInitializer> initializes) {
        return new DelegatingStoreInitializer(initializes);
    }

    @Bean
    public StoreAsyncInitializingExecutor asyncInitializingExecutor(DelegatingStoreInitializer storeInitializer,
                                                                    @Lazy StoreService storeService) {
        return new StoreAsyncInitializingExecutor(storeInitializer, storeService);
    }

    @Bean
    public StoreAsyncInitializingManager storeAsyncInitializingManager(StoreAsyncInitializingExecutor initializingExecutor) {
        return new StoreAsyncInitializingManager(initializingExecutor);
    }

    @Bean
    public StoreRolesInitializer storeRolesInitializer(RoleService roleService) {
        return new StoreRolesInitializer(roleService);
    }

    @Bean
    public StoreStaffsInitializer storeStaffsInitializer(StaffService staffService, RoleService roleService, UserService userService) {
        return new StoreStaffsInitializer(staffService, roleService, userService);
    }

    @Bean
    public StoreAccessResourceInitializer storeAccessResourceInitializer(AccessControlManager accessControlManager) {
        return new StoreAccessResourceInitializer(accessControlManager);
    }

    @Bean
    public StoreBlobInitializer storeBlobInitializer(StoreBlobService blobService) {
        return new StoreBlobInitializer(blobService);
    }
}
