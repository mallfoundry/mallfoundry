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
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InternalStoreService implements StoreService {

    private final StoreRepository storeRepository;

    private final ApplicationEventPublisher eventPublisher;

    public InternalStoreService(StoreRepository storeRepository,
                                ApplicationEventPublisher eventPublisher) {
        this.storeRepository = storeRepository;
        this.eventPublisher = eventPublisher;
    }

    public Optional<Store> getStore(String id) {
        return this.storeRepository.findById(id);
    }

    @Transactional
    public void saveStore(Store store) {
        this.storeRepository.save(store);
    }

    public SliceList<Store> getStores(StoreQuery query) {
        return this.storeRepository.findAll(query);
    }

    @Transactional
    public void createStore(Store store) {
        store.initialize();
        this.storeRepository.save(store);
    }

    @Transactional
    public void cancelStore(String storeId) {
        Store store = this.storeRepository.findById(storeId).orElseThrow();
        this.eventPublisher.publishEvent(new StoreCancelledEvent(store));
        this.storeRepository.delete(store);
    }

    @Override
    public StoreId createStoreId(String id) {
        return new InternalStoreId(id);
    }
}
