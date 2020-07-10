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

public interface OrderService {

    OrderQuery createOrderQuery();

    Order createOrder(String id);

    List<Order> splitOrders(List<Order> orders);

    List<Order> placeOrder(Order order);

    List<Order> placeOrders(List<Order> orders);

    Optional<Order> getOrder(String orderId);

    SliceList<Order> getOrders(OrderQuery query);

    long getOrderCount(OrderQuery query);

    Order updateOrder(Order order);

    void fulfilOrder(String orderId);

    void payOrder(String orderId, PaymentInformation payment);

    void signOrder(String orderId);

    void receiptOrder(String orderId);

    void cancelOrder(String orderId, String reason);

    Shipment addOrderShipment(String orderId, Shipment shipment);

    Optional<Shipment> getOrderShipment(String orderId, String shipmentId);

    List<Shipment> getOrderShipments(String orderId);

    void setOrderShipment(String orderId, Shipment shipment);

    void setOrderShipments(String orderId, List<Shipment> shipments);

    void removeOrderShipment(String orderId, String shipmentId);
}
