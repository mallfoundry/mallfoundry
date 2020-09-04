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

import org.mallfoundry.data.SliceList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StoreService {

    StoreQuery createStoreQuery();

    StoreId createStoreId(String tenantId, String id);

    StoreId createStoreId(String id);

    Store createStore(StoreId id);

    Store createStore(Store store);

    Store getStore(StoreId id);

    Optional<Store> findStore(StoreId id);

    SliceList<Store> getStores(StoreQuery query);

    List<Store> getStores(Collection<StoreId> ids);

    Store updateStore(Store store);

    StoreProgress closeStore(StoreId id);

    StoreProgress initializeStore(StoreId id);

    Optional<StoreProgress> findStoreProgress(StoreId id);

}
