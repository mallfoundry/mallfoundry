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

import org.mallfoundry.store.StoreRepository;
import org.mallfoundry.store.lifecycle.AsyncStoreLifecycleExecutor;
import org.mallfoundry.store.lifecycle.AsyncStoreLifecycleManager;
import org.mallfoundry.store.lifecycle.StoreLifecycle;
import org.mallfoundry.store.lifecycle.StoreLifecycleChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class StoreLifecycleConfiguration {

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