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

package org.mallfoundry.autoconfigure.following;

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.following.DefaultFollowingProductService;
import org.mallfoundry.following.DefaultFollowingStoreService;
import org.mallfoundry.following.FollowingProductProcessor;
import org.mallfoundry.following.FollowingStoreProcessor;
import org.mallfoundry.following.ProductFollowingRepository;
import org.mallfoundry.following.StoreFollowingRepository;
import org.mallfoundry.following.repository.jpa.DelegatingJpaProductFollowingRepository;
import org.mallfoundry.following.repository.jpa.DelegatingJpaStoreFollowingRepository;
import org.mallfoundry.following.repository.jpa.JpaProductFollowingRepository;
import org.mallfoundry.following.repository.jpa.JpaStoreFollowingRepository;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class FollowingAutoConfiguration {

    @Bean
    public DelegatingJpaProductFollowingRepository delegatingJpaProductFollowingRepository(JpaProductFollowingRepository repository) {
        return new DelegatingJpaProductFollowingRepository(repository);
    }

    @Bean
    public DelegatingJpaStoreFollowingRepository delegatingJpaStoreFollowingRepository(JpaStoreFollowingRepository repository) {
        return new DelegatingJpaStoreFollowingRepository(repository);
    }

    @Bean
    @Autowired(required = false)
    public DefaultFollowingProductService defaultFollowingProductService(@Lazy List<FollowingProductProcessor> processors,
                                                                         UserService userService,
                                                                         ProductService productService,
                                                                         ProductFollowingRepository productFollowingRepository) {
        var service = new DefaultFollowingProductService(userService, productService, productFollowingRepository);
        service.setProductProcessors(processors);
        return service;
    }

    @Bean
    @Autowired(required = false)
    public DefaultFollowingStoreService defaultFollowingStoreService(@Lazy List<FollowingStoreProcessor> processors,
                                                                     UserService userService,
                                                                     StoreService storeService,
                                                                     StoreFollowingRepository storeFollowingRepository) {
        var service = new DefaultFollowingStoreService(userService, storeService, storeFollowingRepository);
        service.setProcessors(processors);
        return service;
    }
}
