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

package org.mallfoundry.catalog.collection;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.util.Copies;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DefaultCollectionService implements CollectionService, CollectionProcessorInvoker {

    private List<CollectionProcessor> processors = Collections.emptyList();

    private final CollectionRepository collectionRepository;

    public DefaultCollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void setProcessors(List<CollectionProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public Collection createCollection(String id) {
        return this.collectionRepository.create(id);
    }

    @Override
    public CollectionQuery createCollectionQuery() {
        return new DefaultCollectionQuery();
    }

    @Transactional
    public Collection addCollection(Collection collection) {
        collection = this.invokePreProcessBeforeAddCollection(collection);
        collection.create();
        return this.collectionRepository.save(collection);
    }

    public Collection getCollection(String id) {
        return this.collectionRepository.findById(id)
                .map(this::invokePostProcessAfterGetCollection)
                .orElseThrow();
    }

    @Override
    public List<Collection> getCollections(java.util.Collection<String> ids) {
        return this.collectionRepository.findAllById(ids);
    }

    @Override
    public SliceList<Collection> getCollections(CollectionQuery query) {
        return this.collectionRepository.findAll(query);
    }

    private Collection updateCollection(final Collection source, final Collection collection) {
        Copies.notBlank(source::getName).trim(collection::setName);
        return collection;
    }

    @Transactional
    @Override
    public Collection updateCollection(Collection source) {
        return Function.<Collection>identity()
                .<Collection>compose(collection -> this.updateCollection(source, collection))
                .compose(this::invokePreProcessBeforeUpdateCollection)
                .compose(this::requiredCollection)
                .apply(source.getId());
    }

    @Transactional
    @Override
    public List<Collection> updateCollections(List<Collection> collections) {
        return this.collectionRepository.saveAll(collections);
    }

    private Collection requiredCollection(String id) {
        return this.collectionRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteCollection(String id) {
        var collection = Function.<Collection>identity()
                .compose(this::invokePreProcessBeforeDeleteCollection)
                .compose(this::requiredCollection)
                .apply(id);
        this.collectionRepository.delete(collection);
    }

    @Override
    public Collection invokePreProcessBeforeAddCollection(Collection collection) {
        return Processors.stream(this.processors)
                .map(CollectionProcessor::preProcessBeforeAddCollection)
                .apply(collection);
    }

    @Override
    public Collection invokePreProcessBeforeUpdateCollection(Collection collection) {
        return Processors.stream(this.processors)
                .map(CollectionProcessor::preProcessBeforeUpdateCollection)
                .apply(collection);
    }

    @Override
    public Collection invokePreProcessBeforeDeleteCollection(Collection collection) {
        return Processors.stream(this.processors)
                .map(CollectionProcessor::preProcessBeforeDeleteCollection)
                .apply(collection);
    }

    @Override
    public CollectionQuery invokePreProcessBeforeGetCollections(CollectionQuery query) {
        return Processors.stream(this.processors)
                .map(CollectionProcessor::preProcessBeforeGetCollections)
                .apply(query);
    }

    @Override
    public Collection invokePostProcessAfterGetCollection(Collection collection) {
        return Processors.stream(this.processors)
                .map(CollectionProcessor::postProcessAfterGetCollection)
                .apply(collection);
    }
}
