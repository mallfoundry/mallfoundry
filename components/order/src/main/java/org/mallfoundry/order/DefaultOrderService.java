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
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DefaultOrderService implements OrderService {

    private final OrderConfiguration orderConfiguration;

    private final OrderProcessorsInvoker processorsInvoker;

    private final OrderRepository orderRepository;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultOrderService(OrderConfiguration orderConfiguration,
                               OrderProcessorsInvoker processorsInvoker,
                               OrderRepository orderRepository,
                               OrderSplitter orderSplitter,
                               CarrierService carrierService,
                               ApplicationEventPublisher eventPublisher) {
        this.orderConfiguration = orderConfiguration;
        this.processorsInvoker = processorsInvoker;
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new DefaultOrderQuery();
    }

    @Override
    public Order createOrder(String id) {
        return this.orderRepository.create(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
    }

    @Override
    public List<Order> splitOrders(List<Order> orders) {
        return this.orderSplitter.splitting(orders);
    }

    @Transactional
    @Override
    public List<Order> placeOrder(Order order) {
        return this.placeOrders(List.of(order));
    }

    private <T, R> Function<List<T>, List<T>> publishEvent(Function<T, R> function) {
        return (List<T> tt) -> {
            tt.stream().map(function).forEach(this.eventPublisher::publishEvent);
            return tt;
        };
    }

    private List<Order> invokePlaceOrders(List<Order> orders) {
        orders.forEach(order -> order.place(this.orderConfiguration.getDefaultExpires()));
        return orders;
    }

    @Transactional
    @Override
    public List<Order> placeOrders(List<Order> orders) {
        return Function.<List<Order>>identity()
                .compose(this.publishEvent(ImmutableOrderPlacedEvent::new))
                .compose(this.orderRepository::saveAll)
                .compose(this.processorsInvoker::invokePreProcessPlaceOrders)
                .compose(this::invokePlaceOrders)
                .<List<Order>>compose(this.orderSplitter::splitting)
                .apply(orders);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return this.orderRepository.findById(orderId)
                .map(this.processorsInvoker::invokePostProcessGetOrder);
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        try {
            return Function.<SliceList<Order>>identity()
                    .compose(this.processorsInvoker::invokePostProcessGetOrders)
                    .compose(this.orderRepository::findAll)
                    .compose(this.processorsInvoker::invokePreProcessGetOrders)
                    .apply(query);
        } finally {
            this.processorsInvoker.invokeAfterProcessCompletion();
        }
    }

    @Override
    public long getOrderCount(OrderQuery query) {
        return this.orderRepository.count(query);
    }

    @Transactional
    @Override
    public Order updateOrder(Order order) {
        return Function.<Order>identity()
                .compose(this.orderRepository::save)
                .compose(this.processorsInvoker::invokePreProcessUpdateOrder)
                .apply(order);
    }

    private Order requiredOrder(String orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(OrderExceptions::notFound);
    }

    @Transactional
    @Override
    public void payOrder(String orderId, PaymentInformation details) {
        var order = this.requiredOrder(orderId);
        order.pay(details);
        this.eventPublisher.publishEvent(new ImmutableOrderPaidEvent(order));
    }

    @Transactional
    @Override
    public void fulfilOrder(String orderId) {
        var order = this.processorsInvoker.invokePreProcessFulfilOrder(requiredOrder(orderId));
        order.fulfil();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderFulfilledEvent(savedOrder));
    }

    @Transactional
    @Override
    public void signOrder(String orderId, String message) {
        var order = this.requiredOrder(orderId);
        message = this.processorsInvoker.invokePreProcessSignOrder(order, message);
        order.sign(message);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderSignedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void receiptOrder(String orderId) {
        var order = this.processorsInvoker.invokePreProcessReceiptOrder(this.requiredOrder(orderId));
        order.receipt();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReceivedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId, String reason) {
        var order = requiredOrder(orderId);
        reason = this.processorsInvoker.invokePreProcessCancelOrder(order, reason);
        order.cancel(reason);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderCancelledEvent(savedOrder));
    }

    @Transactional
    @Override
    public Shipment addOrderShipment(String orderId, Shipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.processorsInvoker.invokePreProcessAddOrderShipment(order, shipment);
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
        return Function.<Optional<Shipment>>identity()
                .<Order>compose(order -> order.getShipment(shipmentId))
                .compose(this.processorsInvoker::invokePreProcessGetOrderShipment)
                .compose(this::requiredOrder)
                .apply(orderId);
    }

    @Override
    public List<Shipment> getOrderShipments(String orderId) {
        return Function.<List<Shipment>>identity()
                .<Order>compose(Order::getShipments)
                .compose(this.processorsInvoker::invokePreProcessGetOrderShipments)
                .compose(this::requiredOrder)
                .apply(orderId);
    }

    @Transactional
    @Override
    public void updateOrderShipment(String orderId, Shipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.processorsInvoker.invokePreProcessUpdateOrderShipment(order, shipment);
        order.updateShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void updateOrderShipments(String orderId, List<Shipment> shipments) {
        var order = this.requiredOrder(orderId);
        shipments = this.processorsInvoker.invokePreProcessUpdateOrderShipments(order, shipments);
        order.updateShipments(shipments);
        this.orderRepository.save(order);
    }

    private Shipment requiredOrderShipment(Order order, String shipmentId) {
        return order.getShipment(shipmentId).orElseThrow();
    }

    @Transactional
    @Override
    public void removeOrderShipment(String orderId, String shipmentId) {
        var order = this.requiredOrder(orderId);
        var shipment = this.processorsInvoker.invokePreProcessRemoveOrderShipment(order, this.requiredOrderShipment(order, shipmentId));
        order.removeShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeOrderShipments(String orderId, Set<String> shipmentIds) {
        var order = this.requiredOrder(orderId);
        var shipments = this.processorsInvoker.invokePreProcessRemoveOrderShipments(order, order.getShipments(shipmentIds));
        order.removeShipments(shipments);
        this.orderRepository.save(order);
    }
}
