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
