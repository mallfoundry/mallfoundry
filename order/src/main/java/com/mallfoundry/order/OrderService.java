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

package com.mallfoundry.order;

import com.mallfoundry.payment.PaymentLink;
import com.mallfoundry.payment.PaymentOrder;
import com.mallfoundry.payment.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerValidator customerValidator;

    private final CheckoutCounter checkoutCounter;

    private final OrderSplitter orderSplitter;

    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository,
                        CustomerValidator customerValidator,
                        CheckoutCounter checkoutCounter,
                        OrderSplitter orderSplitter,
                        PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerValidator = customerValidator;
        this.checkoutCounter = checkoutCounter;
        this.orderSplitter = orderSplitter;
        this.paymentService = paymentService;
    }

    @Transactional
    public OrderCreation createOrders(List<Order> orders) throws CustomerValidException {
        customerValidator.validate(orders);
        checkoutCounter.checkout(orders);
        return new OrderCreation(
                this.orderRepository
                        .saveAll(this.orderSplitter.splitting(orders))
                        .stream().map(Order::getId).collect(Collectors.toList()),
                this.totalAmount(orders));
    }

    @Transactional
    public OrderCreation createOrder(Order order) throws CustomerValidException {
        return this.createOrders(List.of(order));
    }

    public BigDecimal totalAmount(PaymentOrder payOrder) {
        List<Order> orders = this.orderRepository.findAllById(payOrder.getOrders());
        return this.totalAmount(orders);
    }

    private BigDecimal totalAmount(List<Order> orders) {
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public PaymentLink createPaymentOrder(PaymentOrder payOrder) {
        List<Order> orders = this.orderRepository.findAllById(payOrder.getOrders());
        orders = Objects.isNull(orders) ? Collections.emptyList() : orders;
        Map<Long, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getId, order -> order));
        for (Long orderId : payOrder.getOrders()) {
            Order order = orderMap.get(orderId);
            if (Objects.isNull(order)) {
                throw new OrderException(String.format("The order(%s)  does not exist.", orderId));
            }
        }

        payOrder.setTotalAmount(this.totalAmount(orders));
        PaymentLink link = this.paymentService.createOrder(payOrder);

        // Set order payment details.
        for (Order order : orders) {
            order.awaitingPayment(new PaymentDetails(link.getId(), payOrder.getProvider(), payOrder.getStatus()));
        }
        return link;
    }

    @Transactional
    public void confirmPayment(PaymentOrder paymentOrder) {
        paymentOrder.getOrders().forEach(orderId -> {
            if (paymentOrder.isSuccess()) {
                this.orderRepository
                        .findById(orderId).
                        ifPresent(Order::paid);
            }
        });

    }

    @Transactional
    public void awaitingShipment(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId)
                .ifPresent(Order::awaitingShipment);
    }

    @Transactional
    public void partiallyShipped(Long orderId, List<OrderShipment> shipments) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(order -> order.partiallyShipped(shipments));
    }

    @Transactional
    public void shipped(Long orderId, List<OrderShipment> shipments) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(order -> order.shipped(shipments));
    }

    @Transactional
    public void awaitingPickup(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::awaitingPickup);
    }

    @Transactional
    public void complete(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::completed);
    }

    @Transactional
    public void dispute(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::disputed);
    }

    @Transactional
    public void partiallyRefunded(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::partiallyRefunded);
    }

    @Transactional
    public void refund(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::refunded);
    }

    @Transactional
    public void cancel(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::cancelled);
    }

    @Transactional
    public void decline(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId).ifPresent(Order::declined);
    }

    public Optional<Order> getOrder(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}
