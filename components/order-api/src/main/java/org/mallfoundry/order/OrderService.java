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
import org.mallfoundry.payment.Payment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 提供对订单对象的管理。
 *
 * @author Zhi Tang
 */
public interface OrderService {

    /**
     * 创建一个订单查询对象。
     *
     * @return 新的订单查询对象
     */
    OrderQuery createOrderQuery();

    /**
     * 创建一个订单对象。
     *
     * @param id 订单标识，可以为 null
     * @return 订单对象
     */
    Order createOrder(String id);

    OrderPayment createOrderPayment();

    /**
     * 拆分订单对象集合。
     *
     * @param orders 需要拆分的订单对象集合
     * @return 拆分后的订单对象集合
     */
    List<Order> splitOrders(List<Order> orders);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    Order getOrder(String orderId);

    Optional<Order> findOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    long countOrders(OrderQuery query);

    Order updateOrder(Order order);

    void payOrder(String orderId, OrderPaymentResult payment);

    void payOrders(Set<String> orderIds, OrderPaymentResult payment);

    /**
     * 打包订单对象。
     *
     * @param orderId 订单标识
     */
    void fulfilOrder(String orderId);

    void signOrder(String orderId, String signMessage);

    void receiptOrder(String orderId);

    void cancelOrder(String orderId, String cancelReason);

    void closeOrders(Set<String> orderIds, String closeReason);

    void declineOrder(String orderId, String declineReason);

    OrderShipment addOrderShipment(String orderId, OrderShipment shipment);

    Optional<OrderShipment> getOrderShipment(String orderId, String shipmentId);

    List<OrderShipment> getOrderShipments(String orderId);

    void updateOrderShipment(String orderId, OrderShipment shipment);

    void updateOrderShipments(String orderId, List<OrderShipment> shipments);

    void removeOrderShipment(String orderId, String shipmentId);

    void removeOrderShipments(String orderId, Set<String> shipmentIds);

    Payment startOrderPayment(OrderPayment orderPayment) throws OrderException;

    OrderRefund applyOrderRefund(String orderId, OrderRefund refund);

    void cancelOrderRefund(String orderId, String refundId);

    void approveOrderRefund(String orderId, String refundId);

    void disapproveOrderRefund(String orderId, String refundId, String disapprovedReason);

    void activeOrderRefund(String orderId, OrderRefund refund);

    Optional<OrderRefund> getOrderRefund(String orderId, String refundId);

    OrderReview addOrderReview(String orderId, OrderReview review);

    List<OrderReview> addOrderReviews(String orderId, List<OrderReview> reviews);
}
