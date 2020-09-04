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

import org.mallfoundry.configuration.ConfigurationManager;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.storage.StorageService;
import org.mallfoundry.store.StoreAddressService;
import org.mallfoundry.store.StoreRepository;
import org.mallfoundry.store.lifecycle.AsyncStoreLifecycleExecutor;
import org.mallfoundry.store.lifecycle.AsyncStoreLifecycleManager;
import org.mallfoundry.store.lifecycle.StoreAccessControlLifecycle;
import org.mallfoundry.store.lifecycle.StoreAddressLifecycle;
import org.mallfoundry.store.lifecycle.StoreBucketLifecycle;
import org.mallfoundry.store.lifecycle.StoreConfigurationLifecycle;
import org.mallfoundry.store.lifecycle.StoreLifecycle;
import org.mallfoundry.store.lifecycle.StoreLifecycleChain;
import org.mallfoundry.store.lifecycle.StoreMemberLifecycle;
import org.mallfoundry.store.lifecycle.StoreRoleLifecycle;
import org.mallfoundry.store.lifecycle.StoreStaffLifecycle;
import org.mallfoundry.store.member.MemberService;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.StaffService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class StoreLifecycleConfiguration {

    @Bean
    public StoreAccessControlLifecycle storeAccessControlLifecycle(AccessControlManager manager) {
        return new StoreAccessControlLifecycle(manager);
    }

    @Bean
    public StoreAddressLifecycle storeAddressLifecycle(StoreAddressService storeAddressService) {
        return new StoreAddressLifecycle(storeAddressService);
    }

    @Bean
    public StoreBucketLifecycle storeBucketLifecycle(StorageService storageService) {
        return new StoreBucketLifecycle(storageService);
    }

    @Bean
    public StoreConfigurationLifecycle storeConfigurationLifecycle(ConfigurationManager configurationManager) {
        return new StoreConfigurationLifecycle(configurationManager);
    }

    @Bean
    public StoreMemberLifecycle storeMemberLifecycle(MemberService memberService) {
        return new StoreMemberLifecycle(memberService);
    }

    @Bean
    public StoreRoleLifecycle storeRoleLifecycle(RoleService roleService) {
        return new StoreRoleLifecycle(roleService);
    }

    @Bean
    public StoreStaffLifecycle storeStaffLifecycle(UserService userService,
                                                   StaffService staffService,
                                                   RoleService roleService) {
        return new StoreStaffLifecycle(userService, staffService, roleService);
    }

    @Bean
    public StoreLifecycleChain storeLifecycleChain(@Lazy List<StoreLifecycle> lifecycles) {
        return new StoreLifecycleChain(lifecycles);
    }

    @Bean
    public AsyncStoreLifecycleExecutor asyncStoreLifecycleExecutor(StoreLifecycleChain lifecycleChain,
                                                                   StoreRepository storeRepository) {
        return new AsyncStoreLifecycleExecutor(lifecycleChain, storeRepository);
    }

    @Bean
    public AsyncStoreLifecycleManager asyncStoreLifecycleManager(AsyncStoreLifecycleExecutor lifecycleExecutor) {
        return new AsyncStoreLifecycleManager(lifecycleExecutor);
    }
}
