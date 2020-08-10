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

import org.mallfoundry.configuration.ConfigurationHolder;
import org.mallfoundry.configuration.ConfigurationIdRetrievalStrategy;
import org.mallfoundry.configuration.ConfigurationManager;
import org.mallfoundry.configuration.ConfigurationRepository;
import org.mallfoundry.configuration.DefaultConfigurationManager;
import org.mallfoundry.configuration.DelegatingConfigurationIdRetrievalStrategy;
import org.mallfoundry.configuration.repository.jpa.DelegatingJpaConfigurationRepository;
import org.mallfoundry.configuration.repository.jpa.JpaConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class ConfigurationAutoConfiguration {

    @Bean
    public DelegatingConfigurationIdRetrievalStrategy delegatingConfigurationIdRetrievalStrategy(@Autowired(required = false)
                                                                                                 @Lazy List<ConfigurationIdRetrievalStrategy> idRetrievalStrategies) {
        return new DelegatingConfigurationIdRetrievalStrategy(idRetrievalStrategies);
    }

    @Bean
    public DelegatingJpaConfigurationRepository delegatingJpaConfigurationRepository(JpaConfigurationRepository repository) {
        return new DelegatingJpaConfigurationRepository(repository);
    }

    @Bean
    public DefaultConfigurationManager defaultConfigurationManager(ConfigurationIdRetrievalStrategy idRetrievalStrategy,
                                                                   ConfigurationRepository repository) {
        return new DefaultConfigurationManager(idRetrievalStrategy, repository);
    }

    @Autowired
    public void setConfigurationManager(ConfigurationManager manager) {
        ConfigurationHolder.setConfigurationManager(manager);
    }
}
