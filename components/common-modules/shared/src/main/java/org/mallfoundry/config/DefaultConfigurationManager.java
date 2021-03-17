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

package org.mallfoundry.config;

import org.mallfoundry.util.ObjectType;
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
    public String createConfigurationId(Object entity) {
        return this.idRetrievalStrategy.getConfigurationId(entity);
    }

    @Override
    public String createConfigurationId(ObjectType objectType, String objectId) {
        return objectType.code() + objectId;
    }

    @Override
    public Configuration createConfiguration(String id) {
        return this.repository.create(id);
    }

    @Override
    public Configuration getConfiguration(Object entity) {
        return this.getConfiguration(this.createConfigurationId(entity));
    }

    @Override
    public Configuration getConfiguration(String id) {
        return this.repository.findById(id)
                .orElseThrow(ConfigurationExceptions::notFound);
    }

    @Transactional
    @Override
    public void saveConfiguration(Configuration configuration) {
        this.repository.save(configuration);
    }

    @Transactional
    @Override
    public void deleteConfiguration(String id) {
        var config = this.getConfiguration(id);
        this.repository.delete(config);
    }

    @Transactional
    @Override
    public void deleteConfiguration(Object entity) {
        this.deleteConfiguration(this.createConfigurationId(entity));
    }
}
