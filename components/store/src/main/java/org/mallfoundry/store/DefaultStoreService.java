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
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DefaultStoreService implements StoreService, StoreProcessorInvoker, ApplicationEventPublisherAware {

    private final StoreLifecycleManager storeLifecycleManager;

    private List<StoreProcessor> processors = Collections.emptyList();

    private final StoreRepository storeRepository;

    private ApplicationEventPublisher eventPublisher;

    public DefaultStoreService(StoreLifecycleManager storeLifecycleManager, StoreRepository storeRepository) {
        this.storeLifecycleManager = storeLifecycleManager;
        this.storeRepository = storeRepository;
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
    public Store createStore(StoreId id) {
        return this.storeRepository.create(id);
    }

    @Transactional
    @Override
    public Store createStore(Store store) {
        store = this.invokePreProcessBeforeCreateStore(store);
        store.changeOwner(SubjectHolder.getSubject().toUser());
        store.create();
        return this.storeRepository.save(store);
    }

    private Store requiredStore(StoreId storeId) {
        return this.storeRepository.findById(storeId)
                .orElseThrow(() -> StoreExceptions.notFound(storeId));
    }

    @Override
    public Store getStore(StoreId storeId) {
        return this.findStore(storeId)
                .orElseThrow(() -> StoreExceptions.notFound(storeId));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Store> findStore(StoreId storeId) {
        return this.storeRepository.findById(storeId)
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
    public List<Store> getStores(Collection<StoreId> ids) {
        return this.storeRepository.findAllById(ids);
    }

    private void updateStore(final Store source, final Store dest) {
        Copies.notBlank(source::getName).trim(dest::setName)
                .notBlank(source::getLogo).trim(dest::setLogo)
                .notBlank(source::getIndustry).trim(dest::setIndustry)
                .notBlank(source::getDescription).trim(dest::setDescription);
    }

    @Transactional
    @Override
    public Store updateStore(Store source) {
        var store = this.requiredStore(source.toId());
        store = this.invokePreProcessBeforeUpdateStore(store);
        this.updateStore(source, store);
        return this.storeRepository.save(store);
    }

    @Transactional
    @Override
    public StoreProgress initializeStore(StoreId id) {
        var store = this.getStore(id);
        store = this.invokePreProcessBeforeInitializeStore(store);
        store.initialize();
        store = this.storeRepository.save(store);
        return this.storeLifecycleManager.initializeStore(store);
    }

    @Transactional
    @Override
    public StoreProgress closeStore(StoreId storeId) {
        var store = this.requiredStore(storeId);
        store = this.invokePreProcessBeforeCloseStore(store);
        store.close();
        store = this.storeRepository.save(store);
        return this.storeLifecycleManager.closeStore(store);
    }

    @Override
    public Optional<StoreProgress> findStoreProgress(StoreId id) {
        return this.storeLifecycleManager.findStoreProgress(id);
    }

    @Override
    public Store invokePreProcessBeforeCreateStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeCreateStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeInitializeStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeInitializeStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeUpdateStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeUpdateStore)
                .apply(store);
    }

    @Override
    public Store invokePreProcessBeforeCloseStore(Store store) {
        return Processors.stream(processors)
                .map(StoreProcessor::preProcessBeforeCloseStore)
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
