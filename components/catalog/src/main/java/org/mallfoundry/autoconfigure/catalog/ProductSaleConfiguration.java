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

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.catalog.product.sales.DefaultProductSaleService;
import org.mallfoundry.catalog.product.sales.ProductSaleRepository;
import org.mallfoundry.catalog.product.sales.repository.jpa.DelegatingJpaProductSaleRepository;
import org.mallfoundry.catalog.product.sales.repository.jpa.JpaProductSaleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class ProductSaleConfiguration {

    @Bean
    public DelegatingJpaProductSaleRepository delegatingJpaProductSaleRepository(EntityManager entityManager, JpaProductSaleRepository repository) {
        return new DelegatingJpaProductSaleRepository(entityManager, repository);
    }

    @Bean
    public DefaultProductSaleService defaultProductSaleService(ProductService productService,
                                                               ProductSaleRepository productSaleRepository) {
        return new DefaultProductSaleService(productService, productSaleRepository);
    }
}
