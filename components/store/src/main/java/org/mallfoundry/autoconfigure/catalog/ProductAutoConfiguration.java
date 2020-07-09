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


import org.mallfoundry.catalog.product.DefaultProductService;
import org.mallfoundry.catalog.product.JdbcProductRepository;
import org.mallfoundry.catalog.product.ProductProcessorsInvoker;
import org.mallfoundry.catalog.product.ProductRepositoryDelegate;
import org.mallfoundry.catalog.product.SearchProductRepository;
import org.mallfoundry.catalog.product.repository.elasticsearch.ElasticsearchProductRepository;
import org.mallfoundry.catalog.product.repository.jpa.JpaProductRepository;
import org.mallfoundry.catalog.product.repository.jpa.JpaProductRepositoryDelegate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@Configuration
public class ProductAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JdbcProductRepository.class)
    @ConditionalOnClass(JpaProductRepository.class)
    public JpaProductRepository jpaProductRepository(JpaProductRepositoryDelegate delegate) {
        return new JpaProductRepository(delegate);
    }

    @Bean
    @ConditionalOnClass(ElasticsearchProductRepository.class)
    @ConditionalOnMissingBean(SearchProductRepository.class)
    public ElasticsearchProductRepository elasticsearchProductRepository(ElasticsearchOperations elasticsearchOperations) {
        return new ElasticsearchProductRepository(elasticsearchOperations);
    }

    @Bean
    @ConditionalOnBean({JdbcProductRepository.class, SearchProductRepository.class})
    public ProductRepositoryDelegate productRepositoryDelegate(JdbcProductRepository jdbcProductRepository,
                                                               SearchProductRepository searchProductRepository) {
        return new ProductRepositoryDelegate(jdbcProductRepository, searchProductRepository);
    }

    @Bean
    @ConditionalOnBean(ProductRepositoryDelegate.class)
    public DefaultProductService defaultProductService(ProductProcessorsInvoker processorsInvoker,
                                                       ProductRepositoryDelegate repository,
                                                       ApplicationEventPublisher publisher) {
        return new DefaultProductService(processorsInvoker, repository, publisher);
    }
}
