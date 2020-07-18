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
import org.mallfoundry.catalog.product.ProductAuthorizer;
import org.mallfoundry.catalog.product.ProductIdentifier;
import org.mallfoundry.catalog.product.ProductProcessorsInvoker;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.catalog.product.SmartProductValidator;
import org.mallfoundry.catalog.product.SearchProductRepository;
import org.mallfoundry.catalog.product.repository.elasticsearch.ElasticsearchProductPersistEventListener;
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
import org.springframework.validation.SmartValidator;

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
    @ConditionalOnBean(ElasticsearchProductRepository.class)
    public ElasticsearchProductPersistEventListener elasticsearchProductPersistEventListener(ElasticsearchProductRepository repository) {
        return new ElasticsearchProductPersistEventListener(repository);
    }

    @Bean
    public ProductAuthorizer productAuthorizer() {
        return new ProductAuthorizer();
    }

    @Bean
    public SmartProductValidator smartProductValidator(SmartValidator validator) {
        return new SmartProductValidator(validator);
    }

    @Bean
    public ProductIdentifier productIdentifier() {
        return new ProductIdentifier();
    }

    @Bean
    @ConditionalOnMissingBean(ProductService.class)
    public DefaultProductService defaultProductService(ProductProcessorsInvoker processorsInvoker,
                                                       JdbcProductRepository jdbcProductRepository,
                                                       SearchProductRepository searchProductRepository,
                                                       ApplicationEventPublisher publisher) {
        return new DefaultProductService(processorsInvoker, jdbcProductRepository, searchProductRepository, publisher);
    }
}
