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

package org.mallfoundry.store.lifecycle;

import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.StoreProgress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class StoreProgressResources {

    private static final Map<String, StoreProgress> PROGRESS_MAP = new ConcurrentHashMap<>();

    public static void addStoreProgress(StoreId storeId, StoreProgress initializing) {
        PROGRESS_MAP.put(storeId.getId(), initializing);
    }

    public static StoreProgress getStoreProgress(StoreId storeId) {
        return PROGRESS_MAP.get(storeId.getId());
    }

    public static void removeStoreProgress(StoreId storeId) {
        PROGRESS_MAP.remove(storeId.getId());
    }

}
