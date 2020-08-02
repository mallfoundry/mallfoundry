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

package org.mallfoundry.product;

import org.mallfoundry.catalog.product.review.Review;
import org.mallfoundry.catalog.product.review.ReviewService;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.OrderReviewedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.stream.Collectors;

@Configuration
public class OrderReviewedToProductReviewer {

    private final ReviewService productReviewService;

    public OrderReviewedToProductReviewer(ReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    private Review assignReview(OrderReview review) {
        return this.productReviewService.createReview(review.getId())
                .toBuilder()
                .orderId(review.getOrderId()).itemId(review.getItemId())
                .productId(review.getProductId()).variantId(review.getVariantId())
                .optionSelections(review.getOptionSelections())
                .author(review.getAuthor())
                .body(review.getBody()).rawBody(review.getRawBody()).bodyType(review.getBodyType())
                .tags(review.getTags()).rating(review.getRating())
                .imageUrls(review.getImageUrls()).videoUrls(review.getVideoUrls())
                .build();
    }

    @EventListener
    public void handleOrderReviewedEvent(OrderReviewedEvent event) {
        var reviews = event.getOrderReviews()
                .stream()
                .map(this::assignReview)
                .collect(Collectors.toUnmodifiableList());
        this.productReviewService.review(reviews);
    }
}
