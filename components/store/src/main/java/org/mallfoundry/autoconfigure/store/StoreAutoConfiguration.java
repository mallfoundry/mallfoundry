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

import org.mallfoundry.configuration.StoreConfigurationIdRetrievalStrategy;
import org.mallfoundry.store.DefaultStoreService;
import org.mallfoundry.store.StoreAuthorizeProcessor;
import org.mallfoundry.store.StoreClosedBasicCleanupEventListener;
import org.mallfoundry.store.StoreIdentityProcessor;
import org.mallfoundry.store.StoreProcessor;
import org.mallfoundry.store.StoreRepository;
import org.mallfoundry.store.initializing.StoreInitializingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
@Import({StoreInitializingConfiguration.class,
        StoreAddressConfiguration.class,
        StoreSecurityConfiguration.class,
        StoreStaffConfiguration.class,
        StoreClosedBasicCleanupEventListener.class})
public class StoreAutoConfiguration {

    @Bean
    public StoreConfigurationIdRetrievalStrategy storeConfigurationIdRetrievalStrategy() {
        return new StoreConfigurationIdRetrievalStrategy();
    }

    @Bean
    public DefaultStoreService defaultStoreService(@Autowired(required = false)
                                                   @Lazy List<StoreProcessor> processors,
                                                   StoreInitializingManager storeInitializingManager,
                                                   StoreRepository storeRepository) {
        var service = new DefaultStoreService(storeInitializingManager, storeRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public StoreIdentityProcessor storeIdentityProcessor() {
        return new StoreIdentityProcessor();
    }

    @Bean
    public StoreAuthorizeProcessor storeAuthorizeProcessor() {
        return new StoreAuthorizeProcessor();
    }
}
