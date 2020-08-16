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

import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public class OrderAuthorizeProcessor implements OrderProcessor {

    @PreAuthorize("hasPermission(#query.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_SEARCH + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SEARCH + "," + OrderAuthorities.ORDER_MANAGE + "," + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public OrderQuery preProcessBeforeGetOrders(OrderQuery query) {
        return query;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_READ + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_READ + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order postProcessAfterGetOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_WRITE + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_WRITE + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessBeforeUpdateOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_FULFIL + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessBeforeFulfilOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_SIGN + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public String preProcessBeforeSignOrder(Order order, String message) {
        return message;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_RECEIPT + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessBeforeReceiptOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_CANCEL + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_CANCEL + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public String preProcessBeforeCancelOrder(Order order, String reason) {
        return reason;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_ADD + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeAddOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_READ + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment postProcessAfterGetOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_READ + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderShipment> postProcessAfterGetOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_WRITE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeUpdateOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_WRITE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessBeforeUpdateOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessBeforeRemoveOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessBeforeRemoveOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_APPLY + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeApplyOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_CANCEL + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeCancelOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_APPROVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeApproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_DISAPPROVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeDisapproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_ACTIVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessBeforeActiveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_READ + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_READ + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund postProcessAfterGetOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_ADD + "')")
    @Override
    public OrderReview preProcessBeforeAddOrderReview(Order order, OrderReview review) {
        return review;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_ADD + "')")
    @Override
    public List<OrderReview> preProcessBeforeAddOrderReviews(Order order, List<OrderReview> reviews) {
        return reviews;
    }
}
