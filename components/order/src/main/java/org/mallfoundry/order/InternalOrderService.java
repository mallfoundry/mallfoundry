/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.order;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.security.SecurityUserHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InternalOrderService implements OrderService {

    static final String ORDER_ID_VALUE_NAME = "order.id";

    static final String ORDER_ITEM_ID_VALUE_NAME = "order.item.id";

    static final String ORDER_SHIPMENT_ID_VALUE_NAME = "order.shipment.id";

    private final OrderRepository orderRepository;

    private final OrderSplitter orderSplitter;

    public InternalOrderService(OrderRepository orderRepository, OrderSplitter orderSplitter) {
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new InternalOrderQuery();
    }

    @Override
    public Order createOrder() {
        return new InternalOrder().toBuilder().customerId(SecurityUserHolder.getUserId()).build();
    }

    @Transactional
    @Override
    public List<Order> placeOrder(Order order) {
        return this.placeOrders(List.of(order));
    }

    @Transactional
    @Override
    public List<Order> placeOrders(List<Order> orders) {
        var splitOrders = this.orderSplitter.splitting(orders).stream().map(InternalOrder::of).collect(Collectors.toList());
        splitOrders.stream()
                .peek(order -> order.setId(PrimaryKeyHolder.next(ORDER_ID_VALUE_NAME)))
                .peek(order -> order.getItems().forEach(item -> item.setId(PrimaryKeyHolder.next(ORDER_ITEM_ID_VALUE_NAME))))
                .forEach(Order::place);
        return CastUtils.cast(this.orderRepository.saveAll(splitOrders));
    }

    @Transactional
    @Override
    public Order updateOrder(Order order) {
        return this.orderRepository.save(InternalOrder.of(order));
    }

    @Transactional
    @Override
    public void payOrder(String orderId, PaymentDetails details) {
        this.orderRepository.findById(orderId).orElseThrow().pay(details);
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId, String reason) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.cancel(reason);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return CastUtils.cast(this.orderRepository.findById(orderId));
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        return CastUtils.cast(this.orderRepository.findAll(query));
    }

    @Transactional
    @Override
    public Shipment addShipment(String orderId, Shipment shipment) {
        var order = this.getOrder(orderId).orElseThrow();

        if (StringUtils.isBlank(shipment.getId())) {
            shipment.setId(PrimaryKeyHolder.next(ORDER_SHIPMENT_ID_VALUE_NAME));
        }

        shipment.toBuilder()
                .consignorId(SecurityUserHolder.getUserId())
                .consignor(SecurityUserHolder.getNickname());

        order.addShipment(shipment);
        return shipment;
    }

    @Override
    public Optional<Shipment> getShipment(String orderId, String shipmentId) {
        return this.orderRepository.findById(orderId).orElseThrow().getShipment(shipmentId);
    }

    @Transactional
    @Override
    public void setShipment(String orderId, Shipment shipment) {
        this.orderRepository.findById(orderId).orElseThrow().setShipment(shipment);
    }

    @Override
    public void setShipments(String orderId, List<Shipment> shipments) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        shipments.forEach(order::setShipment);
    }

    @Transactional
    @Override
    public void removeShipment(String orderId, String shipmentId) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.removeShipment(order.getShipment(shipmentId).orElseThrow());
    }

    @Override
    public Shipment createShipment(String orderId, List<String> itemIds) {
        var order = this.getOrder(orderId).orElseThrow();
        return order.createShipment(null).toBuilder().items(order.getItems(itemIds)).build();
    }
}
