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

package org.mallfoundry.store.repository.jpa;

import org.mallfoundry.store.StoreCollection;
import org.mallfoundry.store.StoreCollectionRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaStoreCollectionRepository implements StoreCollectionRepository {

    private final JpaStoreCollectionRepositoryDelegate repository;

    public JpaStoreCollectionRepository(JpaStoreCollectionRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public StoreCollection create(String id) {
        return new JpaStoreCollection(id);
    }

    @Override
    public StoreCollection save(StoreCollection collection) {
        return this.repository.save(JpaStoreCollection.of(collection));
    }

    @Override
    public void delete(StoreCollection collection) {
        this.repository.delete(JpaStoreCollection.of(collection));
    }

    @Override
    public Optional<StoreCollection> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public List<StoreCollection> findAllByStoreId(String storeId) {
        return CastUtils.cast(this.repository.findAllByStoreId(storeId));
    }
}
