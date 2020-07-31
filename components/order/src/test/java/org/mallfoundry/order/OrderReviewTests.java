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
import org.mallfoundry.order.repository.jpa.JpaOrder;
import org.mallfoundry.test.StaticTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@StaticTest
public class OrderReviewTests {

    @Test
    public void testAddReview() {
        var order = new JpaOrder()
                .toBuilder()
                .item((OrderItem item) -> item.toBuilder().id("i1").productId("p1").variantId("v1").build())
                .item((OrderItem item) -> item.toBuilder().id("i2").productId("p2").variantId("v2").build())
                .build();
        order.receipt();
        order.addReview(order.createReview("1").toBuilder().itemId("i1").build());

        // 重复评论
        var r2 = order.createReview("1").toBuilder().itemId("i1").build();
        assertThatExceptionOfType(OrderReviewException.class)
                .isThrownBy(() -> order.addReview(r2))
                .withMessage(OrderExceptions.Item.reviewed("i1").getMessage());

    }
}
