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

import org.mallfoundry.catalog.product.review.DefaultProductReviewService;
import org.mallfoundry.catalog.product.review.ProductReviewAuthorizer;
import org.mallfoundry.catalog.product.review.ProductReviewIdentifier;
import org.mallfoundry.catalog.product.review.ProductReviewProcessor;
import org.mallfoundry.catalog.product.review.ProductReviewProcessorsInvoker;
import org.mallfoundry.catalog.product.review.ProductReviewRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.JpaProductReviewRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.JpaProductReviewRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class ProductReviewConfiguration {

    @Bean
    public JpaProductReviewRepository jpaProductReviewRepository(JpaProductReviewRepositoryDelegate repositoryDelegate) {
        return new JpaProductReviewRepository(repositoryDelegate);
    }

    @Bean
    public DefaultProductReviewService defaultProductReviewService(ProductReviewProcessorsInvoker processorsInvoker,
                                                                   ProductReviewRepository reviewRepository) {
        return new DefaultProductReviewService(processorsInvoker, reviewRepository);
    }

    @Bean
    @Autowired(required = false)
    public ProductReviewProcessorsInvoker productReviewProcessorsInvoker(@Lazy List<ProductReviewProcessor> processors) {
        return new ProductReviewProcessorsInvoker(processors);
    }

    @Bean
    public ProductReviewIdentifier productReviewIdentifier() {
        return new ProductReviewIdentifier();
    }

    @Bean
    public ProductReviewAuthorizer productReviewAuthorizer() {
        return new ProductReviewAuthorizer();
    }
}
