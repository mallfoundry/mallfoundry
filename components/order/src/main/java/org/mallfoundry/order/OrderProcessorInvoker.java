/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General   License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General   License for more details.
 *
 * You should have received a copy of the GNU General   License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.order;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;

import java.util.List;

public interface OrderProcessorInvoker {

    List<Order> invokePreProcessBeforePlaceOrders(List<Order> orders);

    List<Order> invokePreProcessAfterPlaceOrders(List<Order> orders);

    List<Order> invokePostProcessAfterPlaceOrders(List<Order> orders);

    Order invokePostProcessAfterGetOrder(Order order);

    OrderQuery invokePreProcessBeforeGetOrders(OrderQuery query);

    SliceList<Order> invokePostProcessAfterGetOrders(SliceList<Order> orders);

    Order invokePreProcessBeforeUpdateOrder(Order order);

    Order invokePreProcessAfterUpdateOrder(Order order);

    Order invokePreProcessBeforeFulfilOrder(Order order);

    String invokePreProcessBeforeSignOrder(Order order, String message);

    Order invokePreProcessBeforeReceiptOrder(Order order);

    String invokePreProcessBeforeCancelOrder(Order order, String reason);

    String invokePreProcessBeforeCloseOrders(List<Order> orders, String reason);

    String invokePreProcessBeforeDeclineOrder(Order order, String reason);

    OrderShipment invokePreProcessBeforeAddOrderShipment(Order order, OrderShipment shipment);

    OrderShipment invokePostProcessAfterGetOrderShipment(Order order, OrderShipment shipment);

    List<OrderShipment> invokePostProcessAfterGetOrderShipments(Order order, List<OrderShipment> shipments);

    OrderShipment invokePreProcessBeforeUpdateOrderShipment(Order order, OrderShipment shipment);

    List<OrderShipment> invokePreProcessBeforeUpdateOrderShipments(Order order, List<OrderShipment> shipments);

    OrderShipment invokePreProcessBeforeRemoveOrderShipment(Order order, OrderShipment shipment);

    List<OrderShipment> invokePreProcessBeforeRemoveOrderShipments(Order order, List<OrderShipment> shipments);

    OrderRefund invokePreProcessBeforeApplyOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterApplyOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterApplyOrderRefund(Order order, OrderRefund refund);

    List<OrderRefund> invokePreProcessBeforeApplyOrderRefunds(Order order, List<OrderRefund> refunds);

    List<OrderRefund> invokePreProcessAfterApplyOrderRefunds(Order order, List<OrderRefund> refunds);

    List<OrderRefund> invokePostProcessAfterApplyOrderRefunds(Order order, List<OrderRefund> refunds);

    OrderRefund invokePreProcessBeforeCancelOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterCancelOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterCancelOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessBeforeApproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterApproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterApproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessBeforeDisapproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterDisapproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterDisapproveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessBeforeReapplyOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterReapplyOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterReapplyOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessBeforeActiveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePreProcessAfterActiveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterActiveOrderRefund(Order order, OrderRefund refund);

    OrderRefund invokePostProcessAfterGetOrderRefund(Order order, OrderRefund refund);

    List<OrderReview> invokePreProcessBeforeReviewOrder(Order order, List<OrderReview> reviews);

    List<OrderRating> invokePreProcessBeforeRatingOrder(Order order, List<OrderRating> ratings);

    void invokePostProcessAfterCompletion();
}
