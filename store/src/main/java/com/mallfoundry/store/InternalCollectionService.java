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

import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InternalCollectionService implements CollectionService {

    private static final String COLLECTION_ID_VALUE_NAME = "store.collection.id";

    private final CustomCollectionRepository customCollectionRepository;

    public InternalCollectionService(CustomCollectionRepository customCollectionRepository) {
        this.customCollectionRepository = customCollectionRepository;
    }

    public CustomCollection createCollection(String storeId, String name) {
        return new InternalCustomCollection(PrimaryKeyHolder.next(COLLECTION_ID_VALUE_NAME), storeId, name);
    }

    @Transactional
    public CustomCollection saveCollection(CustomCollection collection) {
        return this.customCollectionRepository.save(InternalCustomCollection.of(collection));
    }

    @Transactional
    public void deleteCollection(String id) {
        this.customCollectionRepository.deleteById(id);
    }

    public Optional<CustomCollection> getCollection(String id) {
        return CastUtils.cast(this.customCollectionRepository.findById(id));
    }

    public List<CustomCollection> getCollections(StoreId storeId) {
        return CastUtils.cast(this.customCollectionRepository.findAllByStoreId(storeId.getIdentifier()));
    }

}
