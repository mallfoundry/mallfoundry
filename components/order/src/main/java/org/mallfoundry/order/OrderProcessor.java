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

import org.mallfoundry.data.SliceList;

import java.util.List;

/**
 * 订单对象处理器。
 *
 * @author Zhi Tang
 */
public interface OrderProcessor {

    default Order postProcessGetOrder(Order order) {
        return order;
    }

    default OrderQuery preProcessGetOrders(OrderQuery query) {
        return query;
    }

    default SliceList<Order> postProcessGetOrders(SliceList<Order> orders) {
        return orders;
    }

    default List<Order> preProcessPlaceOrders(List<Order> orders) {
        return orders;
    }

    default List<Order> postProcessPlaceOrders(List<Order> orders) {
        return orders;
    }

    default Order preProcessUpdateOrder(Order order) {
        return order;
    }

    default Order preProcessFulfilOrder(Order order) {
        return order;
    }

    default String preProcessSignOrder(Order order, String message) {
        return message;
    }

    default Order preProcessReceiptOrder(Order order) {
        return order;
    }

    default String preProcessCancelOrder(Order order, String reason) {
        return reason;
    }

    default OrderShipment preProcessAddOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    default Order preProcessGetOrderShipment(Order order) {
        return order;
    }

    default Order preProcessGetOrderShipments(Order order) {
        return order;
    }

    default OrderShipment preProcessUpdateOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    default List<OrderShipment> preProcessUpdateOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    default OrderShipment preProcessRemoveOrderShipment(Order order, OrderShipment shipment) {
        return shipment;
    }

    default List<OrderShipment> preProcessRemoveOrderShipments(Order order, List<OrderShipment> shipments) {
        return shipments;
    }

    default OrderRefund preProcessApplyOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    default OrderRefund preProcessCancelOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    default OrderRefund preProcessApproveOrderRefund(Order order, OrderRefund refund) {
        return refund;

    }

    default OrderRefund preProcessDisapproveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    default OrderRefund preProcessActiveOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }


    default OrderRefund postProcessGetOrderRefund(Order order, OrderRefund refund) {
        return refund;
    }

    default OrderReview preProcessAddOrderReview(Order order, OrderReview review) {
        return review;
    }

    default void postProcessApproveOrderReview(Order order, OrderReview review) {

    }

    default void postProcessDisapproveOrderReview(Order order, OrderReview review) {

    }

    default void postProcessAfterCompletion() {
    }
}
