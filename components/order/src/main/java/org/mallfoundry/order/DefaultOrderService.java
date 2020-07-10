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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    static final String ORDER_ID_VALUE_NAME = "order.id";

    static final String ORDER_ITEM_ID_VALUE_NAME = "order.item.id";

    static final String ORDER_SHIPMENT_ID_VALUE_NAME = "order.shipment.id";

    static final String ORDER_SHIPMENT_ITEM_ID_VALUE_NAME = "order.shipment.item.id";

    private final OrderProcessorsInvoker processorsInvoker;

    private final OrderRepository orderRepository;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultOrderService(OrderProcessorsInvoker processorsInvoker,
                               OrderRepository orderRepository,
                               OrderSplitter orderSplitter,
                               CarrierService carrierService,
                               ApplicationEventPublisher eventPublisher) {
        this.processorsInvoker = processorsInvoker;
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new InternalOrderQuery();
    }

    @Override
    public Order createOrder(String id) {
        return new InternalOrder(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
    }

    @Override
    public List<Order> splitOrders(List<Order> orders) {
        return this.orderSplitter.splitting(orders).stream().map(InternalOrder::of).collect(Collectors.toList());
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
        List<Order> placedOrders = CastUtils.cast(this.orderRepository.saveAll(placingOrders));
        placedOrders.forEach(order ->
                this.eventPublisher.publishEvent(new ImmutableOrderPlacedEvent(order)));
        return placedOrders;
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return this.orderRepository.findById(orderId).map(this.processorsInvoker::invokeProcessPostGetOrder);
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
    public Order updateOrder(Order order) {
        return this.orderRepository.save(InternalOrder.of(order));
    }

    private Order getNonOrder(String orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(OrderExceptions::notFound);
    }

    @Transactional
    @Override
    public void payOrder(String orderId, PaymentInformation details) {
        var order = getNonOrder(orderId);
        order.pay(details);
        this.eventPublisher.publishEvent(new ImmutableOrderPaidEvent(order));
    }

    @Transactional
    @Override
    public void signOrder(String orderId) {
        var order = getNonOrder(orderId);
        order.sign();
    }

    @Transactional
    @Override
    public void receiptOrder(String orderId) {
        var order = getNonOrder(orderId);
        order.receipt();
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId, String reason) {
        var order = getNonOrder(orderId);
        order.cancel(reason);
    }

    @Transactional
    @Override
    public void fulfilOrder(String orderId) {
        var order = getNonOrder(orderId);
        order.fulfil();
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
