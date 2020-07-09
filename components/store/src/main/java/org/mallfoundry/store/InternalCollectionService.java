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

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalCollectionService implements CollectionService {

    private static final String COLLECTION_ID_VALUE_NAME = "store.collection.id";

    private final CustomCollectionRepository customCollectionRepository;

    public InternalCollectionService(CustomCollectionRepository customCollectionRepository) {
        this.customCollectionRepository = customCollectionRepository;
    }

    @Override
    public CustomCollection createCollection(String id) {
        return new InternalCustomCollection(id);
    }

    public CustomCollection createCollection(String storeId, String name) {
        return new InternalCustomCollection(storeId, name);
    }

    @Transactional
    public CustomCollection addCollection(CustomCollection collection) {
        collection.setId(PrimaryKeyHolder.next(COLLECTION_ID_VALUE_NAME));
        return this.customCollectionRepository.save(InternalCustomCollection.of(collection));
    }

    @Override
    public CustomCollection updateCollection(CustomCollection collection) {
        var storedCollection = this.customCollectionRepository.findById(collection.getId()).orElseThrow();
        if (Objects.nonNull(collection.getName())) {
            storedCollection.setName(collection.getName());
        }
        return this.customCollectionRepository.save(storedCollection);
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
