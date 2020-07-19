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

    /**
     * 拆分订单对象集合。
     *
     * @param orders 需要拆分的订单对象集合
     * @return 拆分后的订单对象集合
     */
    List<Order> splitOrders(List<Order> orders);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    long getOrderCount(OrderQuery query);

    Order updateOrder(Order order);

    void payOrder(String orderId, OrderPayment payment);

    void payOrders(Set<String> orderIds, OrderPayment payment);

    /**
     * 打包订单对象。
     *
     * @param orderId 订单标识
     */
    void fulfilOrder(String orderId);

    void signOrder(String orderId, String message);

    void receiptOrder(String orderId);

    void cancelOrder(String orderId, String reason);

    void cancelOrders(Set<String> orderIds, String reason);

    Shipment addOrderShipment(String orderId, Shipment shipment);

    Optional<Shipment> getOrderShipment(String orderId, String shipmentId);

    List<Shipment> getOrderShipments(String orderId);

    void updateOrderShipment(String orderId, Shipment shipment);

    void updateOrderShipments(String orderId, List<Shipment> shipments);

    void removeOrderShipment(String orderId, String shipmentId);

    void removeOrderShipments(String orderId, Set<String> shipmentIds);

    OrderRefund applyOrderRefund(String orderId, OrderRefund refund);

    void cancelOrderRefund(String orderId, String refundId);

    void approveOrderRefund(String orderId, String refundId);

    void updateOrderRefund(String orderId, OrderRefund refund);

}
