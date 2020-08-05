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

package org.mallfoundry.autoconfigure.store;

import org.mallfoundry.store.DefaultStoreConfiguration;
import org.mallfoundry.store.DefaultStoreService;
import org.mallfoundry.store.StoreConfiguration;
import org.mallfoundry.store.StoreProcessor;
import org.mallfoundry.store.StoreRepository;
import org.mallfoundry.store.blob.StoreBlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
@EnableConfigurationProperties(StoreProperties.class)
@Import({ProductCollectionConfiguration.class,
        StoreAddressConfiguration.class,
        StoreRoleConfiguration.class,
        StoreStaffConfiguration.class})
public class StoreAutoConfiguration {

    @Bean
    public StoreConfiguration storeConfiguration(StoreProperties properties) {
        var config = new DefaultStoreConfiguration();
        config.setDefaultLogo(properties.getDefaultLogo());
        return config;
    }

    @Bean
    public DefaultStoreService defaultStoreService(StoreConfiguration storeConfiguration,
                                                   @Autowired(required = false)
                                                   @Lazy List<StoreProcessor> processors,
                                                   StoreBlobService storeBlobService,
                                                   StoreRepository storeRepository) {
        var service = new DefaultStoreService(storeConfiguration, storeBlobService, storeRepository);
        service.setProcessors(processors);
        return service;
    }
}
