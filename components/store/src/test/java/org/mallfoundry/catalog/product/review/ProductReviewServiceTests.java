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

import org.junit.jupiter.api.Test;
import org.mallfoundry.review.BodyType;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

@StandaloneTest
public class ProductReviewServiceTests {

    @Autowired
    private ReviewService productReviewService;

    @WithUserDetails("mf_1")
    @Test
    public void testCommentProductReview() {
        var reviewId = "6";
        var review = this.productReviewService.createReview(reviewId);
        var c1 = review.createReply("c1");
        c1.setBody("test");
        c1.setRawBody("raw test");
        c1.setBodyType(BodyType.TEXT);
        this.productReviewService.replyReview(reviewId, c1);
    }

    @WithUserDetails("mf_1")
    @Test
    public void testCommentProductReviewWithParent() {
        var reviewId = "6";
        var review = this.productReviewService.createReview(reviewId);
        var c2 = review.createReply("c2");
        c2.setBody("test");
        c2.setRawBody("raw test");
        c2.setBodyType(BodyType.TEXT);
        c2.replyTo(review.createReply("c1"));
        var sc2 = this.productReviewService.replyReview(reviewId, c2);
        sc2.getReplyTo();
    }

    @WithUserDetails("mf_1")
    @Test
    public void testCommentProductReviewWithNullParent() {
        var reviewId = "6";
        var review = this.productReviewService.createReview(reviewId);
        var c2 = review.createReply("c3");
        c2.setBody("test");
        c2.setRawBody("raw test");
        c2.setBodyType(BodyType.TEXT);
        c2.replyTo(review.createReply("c4"));
        this.productReviewService.replyReview(reviewId, c2);
    }
}
