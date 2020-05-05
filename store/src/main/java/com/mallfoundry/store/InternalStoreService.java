/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.store;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.blob.StoreBlobService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InternalStoreService implements StoreService {

    private final StoreBlobService storeBlobService;

    private final StoreRepository storeRepository;

    private final ApplicationEventPublisher eventPublisher;

    public InternalStoreService(StoreBlobService storeBlobService,
                                StoreRepository storeRepository,
                                ApplicationEventPublisher eventPublisher) {
        this.storeRepository = storeRepository;
        this.eventPublisher = eventPublisher;
        this.storeBlobService = storeBlobService;
    }

    @Override
    public StoreId createStoreId(String id) {
        return new InternalStoreId(id);
    }

    @Override
    public Store createStore() {
        return new InternalStore();
    }

    public Optional<InternalStore> getStore(String id) {
        return this.storeRepository.findById(id);
    }

    @Transactional
    public void saveStore(InternalStore store) {
        this.storeRepository.save(store);
    }

    public SliceList<InternalStore> getStores(StoreQuery query) {
        return this.storeRepository.findAll(query);
    }

    @Transactional
    public void createStore(InternalStore store) {
        store.initialize();
        this.storeRepository.save(store);
        this.storeBlobService.initializeBucket(this.createStoreId(store.getId()));
        this.eventPublisher.publishEvent(new InternalStoreInitializedEvent(store));
    }

    @Transactional
    public void cancelStore(String storeId) {
        InternalStore store = this.storeRepository.findById(storeId).orElseThrow();
        this.eventPublisher.publishEvent(new InternalStoreCancelledEvent(store));
        this.storeRepository.delete(store);
    }

}
