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

import org.mallfoundry.catalog.product.review.DefaultReviewService;
import org.mallfoundry.catalog.product.review.ReviewAuthorizer;
import org.mallfoundry.catalog.product.review.ReviewReplyRepository;
import org.mallfoundry.catalog.product.review.ReviewIdentifier;
import org.mallfoundry.catalog.product.review.ReviewProcessor;
import org.mallfoundry.catalog.product.review.ReviewProcessorsInvoker;
import org.mallfoundry.catalog.product.review.ReviewRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.DelegatingJpaReviewReplyRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.JpaReviewReplyRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.DelegatingJpaReviewRepository;
import org.mallfoundry.catalog.product.review.repository.jpa.JpaReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class ProductReviewConfiguration {

    @Bean
    public DelegatingJpaReviewRepository delegatingJpaReviewRepository(JpaReviewRepository repositoryDelegate) {
        return new DelegatingJpaReviewRepository(repositoryDelegate);
    }

    @Bean
    public DelegatingJpaReviewReplyRepository delegatingJpaReviewReplyRepository(JpaReviewReplyRepository repositoryDelegate) {
        return new DelegatingJpaReviewReplyRepository(repositoryDelegate);
    }

    @Bean
    public DefaultReviewService defaultProductReviewService(ReviewProcessorsInvoker processorsInvoker,
                                                            ReviewRepository reviewRepository,
                                                            ReviewReplyRepository commentRepository) {
        return new DefaultReviewService(processorsInvoker, reviewRepository, commentRepository);
    }

    @Bean
    @Autowired(required = false)
    public ReviewProcessorsInvoker productReviewProcessorsInvoker(@Lazy List<ReviewProcessor> processors) {
        return new ReviewProcessorsInvoker(processors);
    }

    @Bean
    public ReviewIdentifier productReviewIdentifier() {
        return new ReviewIdentifier();
    }

    @Bean
    public ReviewAuthorizer productReviewAuthorizer() {
        return new ReviewAuthorizer();
    }
}
