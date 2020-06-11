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

package org.mallfoundry.store;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.blob.StoreBlobService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InternalStoreService implements StoreService {

    private final StoreBlobService storeBlobService;

    private final StoreRepository storeRepository;

    private final StoreConfigPropertyRepository storeConfigPropertyRepository;

    private final ApplicationEventPublisher eventPublisher;

    public InternalStoreService(StoreBlobService storeBlobService,
                                StoreRepository storeRepository,
                                StoreConfigPropertyRepository storeConfigPropertyRepository,
                                ApplicationEventPublisher eventPublisher) {
        this.storeRepository = storeRepository;
        this.storeConfigPropertyRepository = storeConfigPropertyRepository;
        this.eventPublisher = eventPublisher;
        this.storeBlobService = storeBlobService;
    }

    @Override
    public StoreQuery createStoreQuery() {
        return new InternalStoreQuery();
    }

    @Override
    public StoreId createStoreId(String id) {
        return new InternalStoreId(id);
    }

    @Override
    public Store createStore(String id) {
        return new InternalStore(id);
    }

    @Override
    public StoreConfiguration getConfiguration(String storeId) {
        var configProperties = this.storeConfigPropertyRepository.findAllByStoreId(storeId)
                .stream().collect(Collectors.toMap(StoreConfigProperty::getName, StoreConfigProperty::getValue));
        return new StoreMapConfiguration(configProperties);
    }

    @Transactional
    @Override
    public void saveConfiguration(String storeId, StoreConfiguration configuration) {
        configuration.toMap().forEach((name, value) ->
                this.storeConfigPropertyRepository.save(new StoreConfigProperty(storeId, name, value)));
    }

    @Transactional
    @Override
    public Store createStore(Store store) {
        store.initialize();
        var savedStore = this.storeRepository.save(InternalStore.of(store));
        this.storeBlobService.initializeBucket(this.createStoreId(store.getId()));
        this.eventPublisher.publishEvent(new InternalStoreInitializedEvent(store));
        return savedStore;
    }

    @Transactional
    @Override
    public Store updateStore(Store store) {
        return this.storeRepository.save(InternalStore.of(store));
    }

    @Transactional
    @Override
    public void cancelStore(String storeId) {
        InternalStore store = this.storeRepository.findById(storeId).orElseThrow();
        this.eventPublisher.publishEvent(new InternalStoreCancelledEvent(store));
        this.storeRepository.delete(store);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Store> getStore(String id) {
        return CastUtils.cast(this.storeRepository.findById(id));
    }

    @Override
    public SliceList<Store> getStores(StoreQuery query) {
        return CastUtils.cast(this.storeRepository.findAll(query));
    }

}
