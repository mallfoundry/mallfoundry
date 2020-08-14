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
import org.mallfoundry.processor.Processors;
import org.mallfoundry.util.Copies;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DefaultProductCollectionService implements ProductCollectionService, ProductCollectionProcessorInvoker {

    private List<ProductCollectionProcessor> processors = Collections.emptyList();

    private final ProductCollectionRepository collectionRepository;

    public DefaultProductCollectionService(ProductCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void setProcessors(List<ProductCollectionProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public ProductCollection createCollection(String id) {
        return this.collectionRepository.create(id);
    }

    @Transactional
    public ProductCollection addCollection(ProductCollection collection) {
        return Function.<ProductCollection>identity()
                .compose(this.collectionRepository::save)
                .compose(this::invokePreProcessBeforeAddCollection)
                .apply(collection);
    }

    private ProductCollection updateCollection(final ProductCollection source, final ProductCollection dest) {
        Copies.notBlank(source::getName).trim(dest::setName);
        return dest;
    }

    @Override
    public ProductCollection updateCollection(ProductCollection source) {
        return Function.<ProductCollection>identity()
                .<ProductCollection>compose(dest -> this.updateCollection(source, dest))
                .compose(this::invokePreProcessBeforeUpdateCollection)
                .compose(this::requiredCollection)
                .apply(source.getId());
    }

    private ProductCollection requiredCollection(String id) {
        return this.collectionRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteCollection(String id) {
        var collection = Function.<ProductCollection>identity()
                .compose(this::invokePreProcessBeforeDeleteCollection)
                .compose(this::requiredCollection)
                .apply(id);
        this.collectionRepository.delete(collection);
    }

    public Optional<ProductCollection> getCollection(String id) {
        return this.collectionRepository.findById(id)
                .map(this::invokePostProcessAfterGetCollection);
    }

    public List<ProductCollection> getCollections(String storeId) {
        return this.collectionRepository.findAllByStoreId(storeId);
    }

    @Override
    public ProductCollection invokePreProcessBeforeAddCollection(ProductCollection collection) {
        return Processors.stream(this.processors)
                .map(ProductCollectionProcessor::preProcessBeforeAddCollection)
                .apply(collection);
    }

    @Override
    public ProductCollection invokePreProcessBeforeUpdateCollection(ProductCollection collection) {
        return Processors.stream(this.processors)
                .map(ProductCollectionProcessor::preProcessBeforeUpdateCollection)
                .apply(collection);
    }

    @Override
    public ProductCollection invokePreProcessBeforeDeleteCollection(ProductCollection collection) {
        return Processors.stream(this.processors)
                .map(ProductCollectionProcessor::preProcessBeforeDeleteCollection)
                .apply(collection);
    }

    @Override
    public ProductCollectionQuery invokePreProcessBeforeGetCollections(ProductCollectionQuery query) {
        return Processors.stream(this.processors)
                .map(ProductCollectionProcessor::preProcessBeforeGetCollections)
                .apply(query);
    }

    @Override
    public ProductCollection invokePostProcessAfterGetCollection(ProductCollection collection) {
        return Processors.stream(this.processors)
                .map(ProductCollectionProcessor::postProcessAfterGetCollection)
                .apply(collection);
    }
}
