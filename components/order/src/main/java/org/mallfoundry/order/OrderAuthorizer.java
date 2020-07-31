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

import org.mallfoundry.security.acl.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public class OrderAuthorizer implements OrderProcessor {

    @PreAuthorize("hasPermission(#query.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_SEARCH + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SEARCH + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderQuery preProcessGetOrders(OrderQuery query) {
        return query;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_READ + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_READ + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order postProcessGetOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_WRITE + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_WRITE + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessUpdateOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_FULFIL + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessFulfilOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_SIGN + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public String preProcessSignOrder(Order order, String message) {
        return message;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_RECEIPT + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessReceiptOrder(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_CANCEL + "," + OrderAuthorities.ORDER_MANAGE + "') or "
            + "hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_CANCEL + "," + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public String preProcessCancelOrder(Order order, String reason) {
        return reason;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_ADD + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessAddOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_READ + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessGetOrderShipment(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_READ + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public Order preProcessGetOrderShipments(Order order) {
        return order;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_WRITE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessUpdateOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_WRITE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessUpdateOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderShipment preProcessRemoveOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_SHIPMENT_REMOVE + ","
            + OrderAuthorities.ORDER_SHIPMENT_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderShipment> preProcessRemoveOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_APPLY + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessApplyOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_CANCEL + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessCancelOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_APPROVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessApproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_DISAPPROVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessDisapproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REFUND_ACTIVE + ","
            + OrderAuthorities.ORDER_REFUND_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderRefund preProcessActiveOrderRefund(Order order, OrderRefund refund) {
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
    public OrderRefund postProcessGetOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_ADD + "')")
    @Override
    public OrderReview preProcessAddOrderReview(Order order, OrderReview review) {
        return review;
    }

    @PreAuthorize("hasPermission(#order.customerId, '" + Resource.CUSTOMER_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_ADD + "')")
    @Override
    public List<OrderReview> preProcessAddOrderReviews(Order order, List<OrderReview> reviews) {
        return reviews;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_APPROVE + ","
            + OrderAuthorities.ORDER_REVIEW_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderReview preProcessApproveOrderReview(Order order, OrderReview review) {
        return review;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_APPROVE + ","
            + OrderAuthorities.ORDER_REVIEW_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderReview> preProcessApproveOrderReviews(Order order, List<OrderReview> reviews) {
        return reviews;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_DISAPPROVE + ","
            + OrderAuthorities.ORDER_REVIEW_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public OrderReview preProcessDisapproveOrderReview(Order order, OrderReview review) {
        return review;
    }

    @PreAuthorize("hasPermission(#order.storeId, '" + Resource.STORE_TYPE + "', '"
            + OrderAuthorities.ORDER_REVIEW_DISAPPROVE + ","
            + OrderAuthorities.ORDER_REVIEW_MANAGE + ","
            + OrderAuthorities.ORDER_MANAGE + "')")
    @Override
    public List<OrderReview> preProcessDisapproveOrderReviews(Order order, List<OrderReview> reviews) {
        return reviews;
    }
}
