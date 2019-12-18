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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomCollectionService {

    private final CustomCollectionRepository customCollectionRepository;

    public CustomCollectionService(CustomCollectionRepository customCollectionRepository) {
        this.customCollectionRepository = customCollectionRepository;
    }

    @Transactional
    public TopCustomCollection createTopCollection(StoreId storeId, String name) {
        TopCustomCollection collection =
                this.customCollectionRepository.save(new TopCustomCollection(storeId, name));
        CustomCollectionPositions.sort(this.customCollectionRepository.findAllByStoreId(storeId));
        return collection;
    }

    @Transactional
    public ChildCustomCollection addChildCollection(Integer parentId, String name) {
        CustomCollection collection = this.getCollection(parentId);
        ChildCustomCollection childCollection = new ChildCustomCollection(name);
        collection.addChildCollection(childCollection);
        return childCollection;
    }

    @Transactional
    public void deleteCollection(Integer id) {
        this.customCollectionRepository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public <S extends CustomCollection> S getCollection(Integer id) {
        return (S) customCollectionRepository.findById(id);
    }

    public List<TopCustomCollection> getAllCollections(StoreId storeId) {
        return this.customCollectionRepository.findAllByStoreId(storeId);
    }

}
