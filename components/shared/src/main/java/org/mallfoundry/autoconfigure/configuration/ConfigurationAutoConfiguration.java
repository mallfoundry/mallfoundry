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

package org.mallfoundry.autoconfigure.configuration;

import org.mallfoundry.config.ConfigurationHolder;
import org.mallfoundry.config.ConfigurationIdRetrievalStrategy;
import org.mallfoundry.config.ConfigurationManager;
import org.mallfoundry.config.ConfigurationRepository;
import org.mallfoundry.config.DefaultConfigurationManager;
import org.mallfoundry.config.DelegatingConfigurationIdRetrievalStrategy;
import org.mallfoundry.config.repository.jpa.DelegatingJpaConfigurationRepository;
import org.mallfoundry.config.repository.jpa.JpaConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class ConfigurationAutoConfiguration {

    @Bean
    public DelegatingJpaConfigurationRepository delegatingJpaConfigurationRepository(JpaConfigurationRepository repository) {
        return new DelegatingJpaConfigurationRepository(repository);
    }

    @Bean
    public DefaultConfigurationManager defaultConfigurationManager(@Autowired(required = false)
                                                                   @Lazy List<ConfigurationIdRetrievalStrategy> idRetrievalStrategies,
                                                                   ConfigurationRepository repository) {
        var idRetrievalStrategy = new DelegatingConfigurationIdRetrievalStrategy(idRetrievalStrategies);
        return new DefaultConfigurationManager(idRetrievalStrategy, repository);
    }

    @Autowired
    public void setConfigurationManager(ConfigurationManager manager) {
        ConfigurationHolder.setConfigurationManager(manager);
    }
}
