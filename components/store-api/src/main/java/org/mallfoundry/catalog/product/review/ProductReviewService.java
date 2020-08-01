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

import java.util.List;
import java.util.Optional;

public interface ProductReviewService {

    ProductReviewQuery createProductReviewQuery();

    ProductReview createProductReview(String reviewId);

    void addProductReview(ProductReview review) throws ProductReviewException;

    void addProductReviews(List<ProductReview> reviews) throws ProductReviewException;

    void approveProductReview(String reviewId) throws ProductReviewException;

    void disapproveProductReview(String reviewId) throws ProductReviewException;

    Optional<ProductReview> getProductReview(String reviewId);

    SliceList<ProductReview> getProductReviews(ProductReviewQuery query);

    ProductReviewComment commentProductReview(String reviewId, ProductReviewComment comment) throws ProductReviewException;

    ProductReviewReplyComment replyProductComment(String commentId, ProductReviewReplyComment comment) throws ProductReviewException;
}
