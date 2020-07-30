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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreAddress;
import org.mallfoundry.store.StoreAddressQuery;
import org.mallfoundry.store.StoreAddressRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class JpaStoreAddressRepository implements StoreAddressRepository {

    private final JpaStoreAddressRepositoryDelegate repository;

    public JpaStoreAddressRepository(JpaStoreAddressRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public StoreAddress create(String id) {
        return new JpaStoreAddress(id);
    }

    @Override
    public StoreAddress save(StoreAddress address) {
        return this.repository.save(JpaStoreAddress.of(address));
    }

    @Override
    public Optional<StoreAddress> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public SliceList<StoreAddress> findAll(StoreAddressQuery query) {
        return CastUtils.cast(this.repository.findAll(query));
    }

    @Override
    public void delete(StoreAddress address) {
        this.repository.delete(JpaStoreAddress.of(address));
    }
}
