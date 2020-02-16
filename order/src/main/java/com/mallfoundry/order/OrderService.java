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

import com.mallfoundry.payment.PaymentException;
import com.mallfoundry.payment.PaymentOrder;
import com.mallfoundry.payment.PaymentProvider;
import com.mallfoundry.payment.PaymentService;
import com.mallfoundry.store.product.InventoryException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerValidator customerValidator;

    private final CheckoutCounter checkoutCounter;

    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository,
                        CustomerValidator customerValidator,
                        CheckoutCounter checkoutCounter,
                        PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.customerValidator = customerValidator;
        this.checkoutCounter = checkoutCounter;
        this.paymentService = paymentService;
    }

    @Transactional
    public Order createOrder(Order order) throws CustomerValidException {
        this.customerValidator.validate(order.getCustomerId());
        checkoutCounter.checkout(order);
        return this.orderRepository.save(order);
    }

    @Transactional
    public String captureOrder(Long orderId) throws OrderException, InventoryException, PaymentException {
        Order order = this.orderRepository.findById(orderId).orElseThrow();
        order.awaitingPayment();
        return this.paymentService
                .capturePayment(
                        new PaymentOrder(String.valueOf(orderId),
                                "hello world",
                                BigDecimal.valueOf(0.01),
                                PaymentProvider.ALIPAY));
    }

    @Transactional
    public void awaitingFulfillment(Long orderId) throws OrderException {
        this.orderRepository
                .findById(orderId)
                .ifPresent(order -> order.awaitingFulfillment(null));
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
