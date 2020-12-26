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

package org.mallfoundry.rest.order;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.review.OrderReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Reviews")
@RestController
@RequestMapping("/v1")
public class OrderReviewResourceV1 {

    private final OrderReviewService orderReviewService;

    public OrderReviewResourceV1(OrderReviewService orderReviewService) {
        this.orderReviewService = orderReviewService;
    }

    @GetMapping("/orders/reviews")
    public SliceList<OrderReview> getOrderReviews(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                  @RequestParam(name = "store_id", required = false) String storeId,
                                                  @RequestParam(name = "customer_id", required = false) String customerId,
                                                  @RequestParam(name = "order_id", required = false) String orderId) {
        return this.orderReviewService.getOrderReviews(
                this.orderReviewService.createOrderReviewQuery().toBuilder()
                        .page(page).limit(limit)//.sort(aSort -> aSort.from(sort))
                        .storeId(storeId).customerId(customerId).orderId(orderId)
                        .build());
    }
}
