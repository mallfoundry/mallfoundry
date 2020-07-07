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
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    static final String ORDER_ID_VALUE_NAME = "order.id";

    static final String ORDER_ITEM_ID_VALUE_NAME = "order.item.id";

    static final String ORDER_SHIPMENT_ID_VALUE_NAME = "order.shipment.id";

    static final String ORDER_SHIPMENT_ITEM_ID_VALUE_NAME = "order.shipment.item.id";

    private final List<OrderPlugin> plugins;

    private final OrderRepository orderRepository;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    public DefaultOrderService(List<OrderPlugin> plugins,
                               OrderRepository orderRepository,
                               OrderSplitter orderSplitter,
                               CarrierService carrierService) {
        this.plugins = plugins;
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new InternalOrderQuery();
    }

    @Override
    public Order createOrder(String id) {
        return new InternalOrder(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
    }

    @Transactional
    @Override
    public List<Order> placeOrder(Order order) {
        return this.placeOrders(List.of(order));
    }

    @Transactional
    @Override
    public List<Order> placeOrders(List<Order> orders) {
        var placingOrders = this.orderSplitter.splitting(orders).stream()
                .map(InternalOrder::of)
                .peek(order -> order.setId(PrimaryKeyHolder.next(ORDER_ID_VALUE_NAME)))
                .peek(order -> order.getItems().forEach(item -> item.setId(PrimaryKeyHolder.next(ORDER_ITEM_ID_VALUE_NAME))))
                .peek(Order::place)
                .collect(Collectors.toList());
        return CastUtils.cast(this.orderRepository.saveAll(placingOrders));
    }

    @Override
    public List<Order> splitOrders(List<Order> orders) {
        return this.orderSplitter.splitting(orders).stream().map(InternalOrder::of).collect(Collectors.toList());
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

    @Transactional
    @Override
    public void packOrder(String orderId) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.pack();
    }

    @Transactional
    @Override
    public void pickupOrder(String orderId) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.pickup();
    }

    private Order processPreGetOrder(Order order) {
        this.plugins.forEach(plugin -> plugin.preGetOrder(order));
        return order;
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return this.orderRepository.findById(orderId).map(this::processPreGetOrder);
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        return CastUtils.cast(this.orderRepository.findAll(query));
    }

    @Override
    public long getOrderCount(OrderQuery query) {
        return this.orderRepository.count(query);
    }

    @Transactional
    @Override
    public Shipment addOrderShipment(String orderId, Shipment shipment) {
        var order = this.getOrder(orderId).orElseThrow();
        if (StringUtils.isBlank(shipment.getId())) {
            shipment.setId(PrimaryKeyHolder.next(ORDER_SHIPMENT_ID_VALUE_NAME));
        }
        shipment.getItems().forEach(item -> item.setId(PrimaryKeyHolder.next(ORDER_SHIPMENT_ITEM_ID_VALUE_NAME)));
        shipment.setConsignorId(SubjectHolder.getUserId());
        if (StringUtils.isBlank(shipment.getConsignor())) {
            shipment.setConsignor(SubjectHolder.getNickname());
        }
        // Set the tracking carrier name.
        if (StringUtils.isNotBlank(shipment.getTrackingNumber())
                && StringUtils.isBlank(shipment.getTrackingCarrier())) {
            var carrier = this.carrierService.getCarrier(shipment.getShippingProvider()).orElseThrow();
            shipment.setTrackingCarrier(carrier.getName());
        }
        order.addShipment(shipment);
        return shipment;
    }

    @Override
    public Optional<Shipment> getOrderShipment(String orderId, String shipmentId) {
        return this.orderRepository.findById(orderId).orElseThrow().getShipment(shipmentId);
    }

    @Override
    public List<Shipment> getOrderShipments(String orderId) {
        return this.orderRepository.findById(orderId).orElseThrow().getShipments();
    }

    @Transactional
    @Override
    public void setOrderShipment(String orderId, Shipment shipment) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.setShipment(shipment);
        this.orderRepository.save(order);
    }

    @Override
    public void setOrderShipments(String orderId, List<Shipment> shipments) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        shipments.forEach(order::setShipment);
    }

    @Transactional
    @Override
    public void removeOrderShipment(String orderId, String shipmentId) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        order.removeShipment(order.getShipment(shipmentId).orElseThrow());
    }
}