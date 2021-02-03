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

import org.mallfoundry.order.dispute.OrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Resource;
import org.springframework.core.Ordered;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class OrderAuthorizeProcessor implements OrderProcessor {

    @PreAuthorize("hasPermission(#query.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_READ + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "') or "
            + "hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_READ + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderQuery preProcessBeforeGetOrders(OrderQuery query) {
        return query;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_READ + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_READ + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Order postProcessAfterGetOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_WRITE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Order preProcessBeforeUpdateOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_DISCOUNT + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public List<OrderDiscount> preProcessBeforeDiscountOrder(Order order, List<OrderDiscount> discounts) {
        return discounts;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_FULFIL + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Order preProcessBeforeFulfilOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_SIGN + ","
            + AllAuthorities.ORDER_MANAGE + "')")
    @Override
    public String preProcessBeforeSignOrder(Order order, String message) {
        return message;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_RECEIPT + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "')")
    @Override
    public Order preProcessBeforeReceiptOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_CANCEL + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_CANCEL + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public String preProcessBeforeCancelOrder(Order order, String reason) {
        return reason;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_ADD + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeAddOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_READ + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderShipment postProcessAfterGetOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_READ + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public List<OrderShipment> postProcessAfterGetOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_WRITE + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeUpdateOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_WRITE + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessBeforeUpdateOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeRemoveOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + AllAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessBeforeRemoveOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_APPLY + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeApplyOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_CANCEL + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeCancelOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_APPROVE + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeApproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_DISAPPROVE + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeDisapproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_ACTIVE + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeActiveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_READ + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.CUSTOMER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.ORDER_REFUND_READ + ","
            + AllAuthorities.ORDER_REFUND_MANAGE + ","
            + AllAuthorities.ORDER_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderRefund postProcessAfterGetOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_REVIEW + ","
            + AllAuthorities.CUSTOMER_MANAGE + "')")
    @Override
    public List<OrderReview> preProcessBeforeReviewOrder(Order order, List<OrderReview> reviews) {
        return reviews;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + AllAuthorities.ORDER_RATING + ","
            + AllAuthorities.CUSTOMER_MANAGE + "')")
    @Override
    public List<OrderRating> preProcessBeforeRatingOrder(Order order, List<OrderRating> ratings) {
        return ratings;
    }
}
