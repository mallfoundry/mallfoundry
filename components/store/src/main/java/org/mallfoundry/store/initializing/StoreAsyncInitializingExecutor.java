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

import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.StoreInitializing;
import org.mallfoundry.store.StoreService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public class StoreAsyncInitializingExecutor {

    private final StoreInitializer storeInitializer;
    private final StoreService storeService;

    public StoreAsyncInitializingExecutor(StoreInitializer storeInitializer, StoreService storeService) {
        this.storeInitializer = storeInitializer;
        this.storeService = storeService;
    }

    @Async
    @Transactional
    public void initializingStore(Store store) {
        SubjectHolder.switchTo().systemUser()
                .doRun(() -> {
                    this.doInitializingStore(store);
                    this.storeService.updateStore(store);
                });
    }

    private void doInitializingStore(Store store) {
        var initializing = new DefaultStoreInitializing();
        try {
            var storeId = store.toId();
            StoreInitializingResources.removeStoreInitializing(storeId);
            StoreInitializingResources.addStoreInitializing(storeId, initializing);
            initializing.initialize();
            storeInitializer.doInitialize(store);
            initializing.configure();
            storeInitializer.doConfigure(store);
            initializing.complete();
        } catch (Exception e) {
            initializing.fail();
            initializing.addStage(e.getMessage()).failure();
            throw e;
        }
    }

    public StoreInitializing getStoreInitializing(StoreId storeId) {
        return StoreInitializingResources.getStoreInitializing(storeId);
    }
}
