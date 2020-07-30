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
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.store.blob.StoreBlobService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultStoreService implements StoreService {

    private final StoreConfiguration storeConfiguration;

    private final StoreBlobService storeBlobService;

    private final StoreRepository storeRepository;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultStoreService(StoreConfiguration storeConfiguration,
                               StoreBlobService storeBlobService,
                               StoreRepository storeRepository,
                               ApplicationEventPublisher eventPublisher) {
        this.storeConfiguration = storeConfiguration;
        this.storeRepository = storeRepository;
        this.eventPublisher = eventPublisher;
        this.storeBlobService = storeBlobService;
    }

    @Override
    public StoreQuery createStoreQuery() {
        return new DefaultStoreQuery();
    }

    @Override
    public Store createStore(String id) {
        return this.storeRepository.create(id);
    }

    @Transactional
    @Override
    public Store createStore(Store store) {
        store.setOwnerId(SubjectHolder.getUserId());
        if (Objects.isNull(store.getLogo())) {
            store.setLogo(storeConfiguration.getDefaultLogo());
        }
        store.initialize();
        var savedStore = this.storeRepository.save(store);
        this.storeBlobService.initializeBucket(store.getId());
        this.eventPublisher.publishEvent(new ImmutableStoreInitializedEvent(store));
        return savedStore;
    }

    @Transactional
    @Override
    public Store updateStore(Store store) {
        return this.storeRepository.save(store);
    }

    @Transactional
    @Override
    public void cancelStore(String storeId) {
        var store = this.storeRepository.findById(storeId).orElseThrow();
        this.eventPublisher.publishEvent(new ImmutableStoreCancelledEvent(store));
        this.storeRepository.delete(store);
    }

    @Override
    public boolean existsStore(String id) {
        return this.storeRepository.findById(id).isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Store> getStore(String id) {
        return this.storeRepository.findById(id);
    }

    @Override
    public SliceList<Store> getStores(StoreQuery query) {
        return this.storeRepository.findAll(query);
    }

}
