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

import org.mallfoundry.config.ConfigurationManager;
import org.mallfoundry.config.ConfigurationScope;
import org.mallfoundry.store.Store;
import org.springframework.core.annotation.Order;

import static org.mallfoundry.store.lifecycle.StoreLifecycle.INITIAL_POSITION;

@Order(INITIAL_POSITION)
public class StoreConfigurationLifecycle implements StoreLifecycle {

    private final ConfigurationManager configurationManager;

    public StoreConfigurationLifecycle(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @Override
    public void doInitialize(Store store) {
        var stage = StoreProgressResources.getStoreProgress(store.toId()).addStage("商铺配置信息初始化");
        try {
            var tenantConfigId = this.configurationManager.createConfigurationId(ConfigurationScope.TENANT, store.getTenantId());
            var storeConfigId = this.configurationManager.createConfigurationId(store);
            var storeConfig = this.configurationManager.createConfiguration(tenantConfigId).createConfiguration(storeConfigId);
            this.configurationManager.saveConfiguration(storeConfig);
            stage.success();
        } catch (RuntimeException e) {
            stage.failure();
            throw e;
        }
    }

    @Override
    public void doClose(Store store) {
        this.configurationManager.deleteConfiguration(store);
    }

    @Override
    public int getPosition() {
        return INITIAL_POSITION;
    }
}
