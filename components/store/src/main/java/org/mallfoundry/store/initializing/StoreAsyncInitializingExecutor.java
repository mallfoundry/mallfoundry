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

import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.StoreInitializing;
import org.springframework.scheduling.annotation.Async;

public class StoreAsyncInitializingExecutor {

    private final StoreInitializer storeInitializer;

    public StoreAsyncInitializingExecutor(StoreInitializer storeInitializer) {
        this.storeInitializer = storeInitializer;
    }

    @Async
    public void initializingStore(Store store) {
        var storeId = store.toId();
        var initializing = new DefaultStoreInitializing();
        StoreInitializingResources.addStoreInitializing(storeId, initializing);
        initializing.initialize();
        storeInitializer.doInitialize(store);
        initializing.configure();
        storeInitializer.doConfigure(store);
        initializing.build();
        storeInitializer.doBuild(store);
        initializing.complete();
    }

    public StoreInitializing getStoreInitializing(StoreId storeId) {
        return StoreInitializingResources.getStoreInitializing(storeId);
    }
}
