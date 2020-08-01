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

package org.mallfoundry.order;

import org.junit.jupiter.api.Test;
import org.mallfoundry.review.BodyType;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@StandaloneTest
public class OrderReviewServiceTests {

    @Autowired
    private OrderService orderService;

    @WithUserDetails("mf_1")
    @Rollback(false)
    @Transactional
    @Test
    public void testAddReview() {
        var orderId = "229";
        var review = this.orderService.createOrder(orderId).createReview(null)
                .toBuilder()
                .itemId("523")
                .body("非常好").rawBody("真的非常好").bodyType(BodyType.TEXT)
                .build();
        this.orderService.addOrderReview(orderId, review);
    }
}
