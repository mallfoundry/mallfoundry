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

package org.mallfoundry.configuration;

import org.springframework.transaction.annotation.Transactional;

public class DefaultConfigurationManager implements ConfigurationManager {

    private final ConfigurationIdRetrievalStrategy idRetrievalStrategy;

    private final ConfigurationRepository repository;

    public DefaultConfigurationManager(ConfigurationIdRetrievalStrategy idRetrievalStrategy,
                                       ConfigurationRepository repository) {
        this.idRetrievalStrategy = idRetrievalStrategy;
        this.repository = repository;
    }

    @Override
    public ConfigurationId createConfigurationId(Object entity) {
        return this.idRetrievalStrategy.getConfigurationId(entity);
    }

    @Override
    public ConfigurationId createConfigurationId(String tenantId, ConfigurationScope scope, String id) {
        return new ImmutableConfigurationId(tenantId, scope, id);
    }

    @Override
    public Configuration createConfiguration(ConfigurationId configId) {
        return this.repository.create(configId);
    }

    private Configuration requiredConfiguration(ConfigurationId configId) {
        return this.repository.findById(configId).orElseThrow();
    }

    @Override
    public Configuration getConfiguration(Object entity) {
        return this.getConfiguration(this.createConfigurationId(entity));
    }

    @Override
    public Configuration getConfiguration(ConfigurationId configId) {
        return this.requiredConfiguration(configId);
    }

    @Transactional
    @Override
    public void saveConfiguration(Configuration configuration) {
        this.repository.save(configuration);
    }

    @Override
    public void deleteConfiguration(ConfigurationId configId) {
        var config = this.requiredConfiguration(configId);
        this.repository.delete(config);
    }

    @Override
    public void deleteConfiguration(Object entity) {
        this.deleteConfiguration(this.createConfigurationId(entity));
    }
}
