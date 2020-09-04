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

import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public class AsyncStoreLifecycleExecutor {

    private final StoreLifecycle lifecycle;

    private final StoreRepository storeRepository;

    public AsyncStoreLifecycleExecutor(StoreLifecycle lifecycle, StoreRepository storeRepository) {
        this.lifecycle = lifecycle;
        this.storeRepository = storeRepository;
    }

    @Async
    @Transactional
    public void doInitialize(Store store) {
        SubjectHolder.switchTo().systemUser()
                .doRun(() -> {
                    this.lifecycle.doInitialize(store);
                    this.storeRepository.save(store);
                });
    }

    @Transactional
    public void doClose(Store store) {
        SubjectHolder.switchTo().systemUser()
                .doRun(() -> {
                    this.lifecycle.doClose(store);
                    this.storeRepository.delete(store);
                });
    }
}
