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

package org.mallfoundry.store.configuration;

import org.mallfoundry.configuration.ConfigurationId;
import org.mallfoundry.configuration.ConfigurationIdRetrievalStrategy;
import org.mallfoundry.configuration.ConfigurationScope;
import org.mallfoundry.configuration.ImmutableConfigurationId;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreId;

public class StoreConfigurationIdRetrievalStrategy implements ConfigurationIdRetrievalStrategy {

    @Override
    public ConfigurationId getConfigurationId(Object entity) {
        if (entity instanceof Store) {
            var store = (Store) entity;
            return new ImmutableConfigurationId(store.getTenantId(), ConfigurationScope.STORE, store.getId());
        } else if (entity instanceof StoreId) {
            var storeId = (StoreId) entity;
            return new ImmutableConfigurationId(storeId.getTenantId(), ConfigurationScope.STORE, storeId.getId());
        }
        return null;
    }
}
