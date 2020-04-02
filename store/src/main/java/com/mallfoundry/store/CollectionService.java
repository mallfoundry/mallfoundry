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

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CustomCollectionRepository customCollectionRepository;

    public CollectionService(CustomCollectionRepository customCollectionRepository) {
        this.customCollectionRepository = customCollectionRepository;
    }

    public CustomCollection createCollection(InternalStoreId storeId, String name) {
        CustomCollection collection = new CustomCollection(storeId, name);
        collection.create();
        return collection;
    }

    @Transactional
    public CustomCollection saveCollection(CustomCollection collection) {
        List<CustomCollection> collections = this.getCollections(collection.getStoreId());
        collection.setPosition(CollectionUtils.size(collections));
        return this.customCollectionRepository.save(collection);
    }

    @Transactional
    public void deleteCollection(Long id) {
        this.customCollectionRepository.deleteById(id);
    }

    public Optional<CustomCollection> getCollection(Long id) {
        return this.customCollectionRepository.findById(id);
    }

    public List<CustomCollection> getCollections(InternalStoreId storeId) {
        return this.customCollectionRepository.findAllByStoreId(storeId);
    }

}
