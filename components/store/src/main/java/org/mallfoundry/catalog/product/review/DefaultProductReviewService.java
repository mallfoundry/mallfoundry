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

package org.mallfoundry.catalog.product.review;

import org.mallfoundry.data.SliceList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DefaultProductReviewService implements ProductReviewService {

    private final ProductReviewProcessorsInvoker processorsInvoker;

    private final ProductReviewRepository reviewRepository;

    public DefaultProductReviewService(ProductReviewProcessorsInvoker processorsInvoker, ProductReviewRepository reviewRepository) {
        this.processorsInvoker = processorsInvoker;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductReviewQuery createProductReviewQuery() {
        return new DefaultProductReviewQuery();
    }

    @Override
    public ProductReview createProductReview(String reviewId) {
        return this.reviewRepository.create(reviewId);
    }

    @Transactional
    @Override
    public void addProductReview(ProductReview review) throws ProductReviewException {
        review = this.processorsInvoker.invokePreProcessAddProductReview(review);
        review.review();
        this.reviewRepository.save(review);
    }

    @Transactional
    @Override
    public void addProductReviews(List<ProductReview> reviews) throws ProductReviewException {
        reviews.forEach(ProductReview::review);
        reviews = this.processorsInvoker.invokePreProcessAddProductReviews(reviews);
        this.reviewRepository.saveAll(reviews);
    }

    @Transactional
    @Override
    public void approveProductReview(String reviewId) throws ProductReviewException {

    }

    @Transactional
    @Override
    public void disapproveProductReview(String reviewId) throws ProductReviewException {

    }

    @Override
    public Optional<ProductReview> getProductReview(String reviewId) {
        return this.reviewRepository.findById(reviewId);
    }

    @Override
    public SliceList<ProductReview> getProductReviews(ProductReviewQuery query) {
        return this.reviewRepository.findAll(query);
    }

    @Override
    public ProductReviewComment commentProductReview(String reviewId, ProductReviewComment comment) throws ProductReviewException {
        return null;
    }

    @Override
    public ProductReviewReplyComment replyProductComment(String commentId, ProductReviewReplyComment comment) throws ProductReviewException {
        return null;
    }
}
