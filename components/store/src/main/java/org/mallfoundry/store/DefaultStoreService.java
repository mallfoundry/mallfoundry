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

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.store.blob.StoreBlobService;
import org.mallfoundry.store.initializing.StoreInitializingManager;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class DefaultStoreService implements StoreService, StoreProcessorInvoker, ApplicationEventPublisherAware {

    private final StoreConfiguration storeConfiguration;

    private final StoreInitializingManager storeInitializingManager;

    private List<StoreProcessor> processors = Collections.emptyList();

    private final StoreBlobService storeBlobService;

    private final StoreRepository storeRepository;

    private ApplicationEventPublisher eventPublisher;

    public DefaultStoreService(StoreConfiguration storeConfiguration,
                               StoreInitializingManager storeInitializingManager,
                               StoreBlobService storeBlobService,
                               StoreRepository storeRepository) {
        this.storeConfiguration = storeConfiguration;
        this.storeInitializingManager = storeInitializingManager;
        this.storeRepository = storeRepository;
        this.storeBlobService = storeBlobService;
    }

    public void setProcessors(List<StoreProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Override
    public StoreQuery createStoreQuery() {
        return new DefaultStoreQuery();
    }

    @Override
    public StoreId createStoreId(String tenantId, String id) {
        return new ImmutableStoreId(tenantId, id);
    }

    @Override
    public StoreId createStoreId(String id) {
        return new ImmutableStoreId(id);
    }

    @Override
    public Store createStore(String id) {
        return this.storeRepository.create(id);
    }

    @Transactional
    @Override
    public Store createStore(Store store) {
        store = this.invokePreProcessBeforeCreateStore(store);
        store.changeOwner(SubjectHolder.getSubject().toUser());
        if (Objects.isNull(store.getLogo())) {
            store.setLogo(storeConfiguration.getDefaultLogo());
        }
        store.create();
        store = this.storeRepository.save(store);
        this.storeBlobService.initializeBucket(store.getId());
        this.eventPublisher.publishEvent(new ImmutableStoreInitializedEvent(store));
        return store;
    }

    @Transactional
    @Override
    public StoreInitializing initializeStore(String id) {
        var store = this.getStore(id);
        store.initialize();
        var savedStore = this.storeRepository.save(store);
        return this.storeInitializingManager.initializeStore(savedStore);
    }

    @Override
    public Optional<StoreInitializing> getStoreInitializing(String id) {
        return this.storeInitializingManager.getStoreInitializing(id);
    }

    @Override
    public Store getStore(String storeId) {
        return this.storeRepository.findById(storeId).orElseThrow();
    }

    private Store updateStore(final Store source, final Store dest) {
        Copies.notBlank(source::getName).trim(dest::setName);
        Copies.notBlank(source::getLogo).trim(dest::setLogo);
        Copies.notBlank(source::getIndustry).trim(dest::setIndustry);
        Copies.notBlank(source::getDescription).trim(dest::setDescription);
        return dest;
    }

    @Transactional
    @Override
    public Store updateStore(Store source) {
        return Function.<Store>identity()
                .compose(this.storeRepository::save)
                .<Store>compose(aStore -> this.updateStore(source, aStore))
                .compose(this::invokePreProcessBeforeUpdateStore)
                .compose(this::getStore)
                .compose(Store::getId)
                .apply(source);
    }

    @Transactional
    @Override
    public void cancelStore(String storeId) {
        var store = Function.<Store>identity()
                .compose(this::invokePreProcessBeforeCancelStore)
                .compose(this::getStore)
                .apply(storeId);
        this.storeRepository.delete(store);
        this.eventPublisher.publishEvent(new ImmutableStoreCancelledEvent(store));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsStore(String id) {
        return this.storeRepository.findById(id)
                .map(this::invokePreProcessBeforeExistsStore)
                .isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Store> findStore(String id) {
        return this.storeRepository.findById(id)
                .map(this::invokePostProcessAfterGetStore);
    }

    @Transactional(readOnly = true)
    @Override
    public SliceList<Store> getStores(StoreQuery query) {
        return Function.<SliceList<Store>>identity()
                .compose(this.storeRepository::findAll)
                .compose(this::invokePreProcessBeforeGetStores)
                .apply(query);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Store> getStores(Collection<String> ids) {
        return this.storeRepository.findAllById(ids);
    }

    @Override
    public Store invokePreProcessBeforeCreateStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeCreateStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeUpdateStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeUpdateStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeCancelStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeCancelStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeExistsStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeExistsStore)
                .apply(store);
    }

    @Override
    public StoreQuery invokePreProcessBeforeGetStores(StoreQuery query) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeGetStores)
                .apply(query);
    }

    @Override
    public Store invokePostProcessAfterGetStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::postProcessAfterGetStore)
                .apply(store);
    }
}
