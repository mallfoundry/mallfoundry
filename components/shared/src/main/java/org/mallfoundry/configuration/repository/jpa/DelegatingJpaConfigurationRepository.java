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

package org.mallfoundry.configuration.repository.jpa;

import org.mallfoundry.configuration.Configuration;
import org.mallfoundry.configuration.ConfigurationId;
import org.mallfoundry.configuration.ConfigurationRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaConfigurationRepository implements ConfigurationRepository {

    private final JpaConfigurationRepository repository;

    public DelegatingJpaConfigurationRepository(JpaConfigurationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Configuration create(ConfigurationId configId) {
        return new JpaConfiguration(configId);
    }

    @Override
    public Optional<Configuration> findById(ConfigurationId configId) {
        return CastUtils.cast(this.repository.findById(JpaConfigurationId.of(configId)));
    }

    @Override
    public Configuration save(Configuration config) {
        return this.repository.save(JpaConfiguration.of(config));
    }

    @Override
    public void delete(Configuration config) {
        this.repository.delete(JpaConfiguration.of(config));
    }
}
