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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;
import org.springframework.core.Ordered;

import java.util.List;

@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderIdentityProcessor implements OrderProcessor {

    private static final String ORDER_ID_VALUE_NAME = "order.id";

    private static final String ORDER_ITEM_ID_VALUE_NAME = "order.item.id";

    private static final String ORDER_SHIPMENT_ID_VALUE_NAME = "order.shipment.id";

    private static final String ORDER_SHIPMENT_ITEM_ID_VALUE_NAME = "order.shipment.item.id";

    private static final String ORDER_REFUND_ID_VALUE_NAME = "order.refund.id";

    private static final String ORDER_REFUND_ITEM_ID_VALUE_NAME = "order.refund.item.id";

    private static final String ORDER_REVIEW_ID_VALUE_NAME = "order.review.id";

    @Override
    public List<Order> preProcessAfterPlaceOrders(List<Order> orders) {
        orders.stream().peek(order -> order.setId(PrimaryKeyHolder.next(ORDER_ID_VALUE_NAME)))
                .forEach(order -> order.getItems().forEach(item -> item.setId(PrimaryKeyHolder.next(ORDER_ITEM_ID_VALUE_NAME))));
        return orders;
    }

    @Override
    public OrderShipment preProcessBeforeAddOrderShipment(Order order, OrderShipment shipment) {
        if (StringUtils.isBlank(shipment.getId())) {
            shipment.setId(PrimaryKeyHolder.next(ORDER_SHIPMENT_ID_VALUE_NAME));
        }
        shipment.getItems().forEach(item -> item.setId(PrimaryKeyHolder.next(ORDER_SHIPMENT_ITEM_ID_VALUE_NAME)));
        return shipment;
    }

    @Override
    public OrderRefund preProcessBeforeApplyOrderRefund(Order order, OrderRefund refund) {
        refund.setId(PrimaryKeyHolder.next(ORDER_REFUND_ID_VALUE_NAME));
        return refund;
    }

    @Override
    public OrderRefund preProcessBeforeActiveOrderRefund(Order order, OrderRefund refund) {
        return this.preProcessBeforeApplyOrderRefund(order, refund);
    }

    @Override
    public List<OrderRefund> preProcessBeforeApplyOrderRefunds(Order order, List<OrderRefund> refunds) {
        refunds.forEach(refund -> refund.setId(PrimaryKeyHolder.next(ORDER_REFUND_ID_VALUE_NAME)));
        return refunds;
    }

    @Override
    public OrderReview preProcessBeforeAddOrderReview(Order order, OrderReview review) {
        review.setId(PrimaryKeyHolder.next(ORDER_REVIEW_ID_VALUE_NAME));
        return review;
    }

    @Override
    public List<OrderReview> preProcessBeforeAddOrderReviews(Order order, List<OrderReview> reviews) {
        reviews.forEach(review -> review.setId(PrimaryKeyHolder.next(ORDER_REVIEW_ID_VALUE_NAME)));
        return reviews;
    }
}
