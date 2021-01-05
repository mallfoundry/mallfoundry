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

package org.mallfoundry.autoconfigure.catalog;

import org.mallfoundry.catalog.collection.CollectionService;
import org.mallfoundry.catalog.collection.DefaultCollectionService;
import org.mallfoundry.catalog.collection.CollectionAuthorizeProcessor;
import org.mallfoundry.catalog.collection.CollectionIdentityProcessor;
import org.mallfoundry.catalog.collection.CollectionProcessor;
import org.mallfoundry.catalog.collection.CollectionRepository;
import org.mallfoundry.catalog.collection.repository.CollectionProductsCountProcessor;
import org.mallfoundry.catalog.collection.repository.jpa.DelegatingJpaCollectionRepository;
import org.mallfoundry.catalog.collection.repository.jpa.JpaCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class CollectionConfiguration {

    @Bean
    public DelegatingJpaCollectionRepository delegatingJpaCollectionRepository(JpaCollectionRepository repositoryDelegate) {
        return new DelegatingJpaCollectionRepository(repositoryDelegate);
    }

    @Bean
    public DefaultCollectionService defaultCollectionService(@Autowired(required = false)
                                                             @Lazy List<CollectionProcessor> processors,
                                                             CollectionRepository repository) {
        var service = new DefaultCollectionService(repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public CollectionIdentityProcessor collectionIdentityProcessor() {
        return new CollectionIdentityProcessor();
    }

    @Bean
    public CollectionAuthorizeProcessor collectionAuthorizeProcessor() {
        return new CollectionAuthorizeProcessor();
    }

    @Bean
    public CollectionProductsCountProcessor collectionProductsCountProcessor(CollectionService collectionService) {
        return new CollectionProductsCountProcessor(collectionService);
    }
}
