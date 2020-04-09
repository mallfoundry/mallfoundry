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

import com.mallfoundry.data.SliceList;
import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.payment.PaymentLink;
import com.mallfoundry.payment.PaymentOrder;
import com.mallfoundry.payment.PaymentService;
import com.mallfoundry.security.SecurityUserHolder;
import com.mallfoundry.store.product.ProductService;
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
public class InternalOrderService implements OrderService {

    String ORDER_ID_VALUE_NAME = "order.id";

    String ORDER_ITEM_ID_VALUE_NAME = "order.item.id";

    String SHIPMENT_VALUE_NAME = "shipment.id";

    private final OrderRepository orderRepository;

    private final CustomerValidator customerValidator;

    private final CheckoutCounter checkoutCounter;

    private final OrderSplitter orderSplitter;

    private final PaymentService paymentService;

    private final ProductService productService;

    public InternalOrderService(OrderRepository orderRepository,
                                CustomerValidator customerValidator,
                                CheckoutCounter checkoutCounter,
                                OrderSplitter orderSplitter,
                                PaymentService paymentService,
                                ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerValidator = customerValidator;
        this.checkoutCounter = checkoutCounter;
        this.orderSplitter = orderSplitter;
        this.paymentService = paymentService;
        this.productService = productService;
    }

    @Override
    public BillingAddress createBillingAddress(
            String countryCode, String postalCode, String consignee, String mobile, String address, String location) {
        return new InternalBillingAddress().toBuilder().countryCode(countryCode).postalCode(postalCode)
                .consignee(consignee).mobile(mobile).address(address).location(location).build();
    }

    @Override
    public ShippingAddress createShippingAddress(
            String countryCode, String postalCode, String consignee, String mobile, String address, String location) {
        return new InternalShippingAddress().toBuilder().countryCode(countryCode).postalCode(postalCode)
                .consignee(consignee).mobile(mobile).address(address).location(location).build();
    }

    @Override
    public Order createOrder(BillingAddress billingAddress, ShippingAddress shippingAddress, List<OrderItem> items) {
        var order = new InternalOrder(billingAddress, shippingAddress, items);
        order.setId(PrimaryKeyHolder.next(ORDER_ID_VALUE_NAME));
        order.setCustomerId(SecurityUserHolder.getUserId());
        return order;
    }

    @Override
    public OrderItem createOrderItem(String productId, String variantId, int quantity) {
        var item = new InternalOrderItem(productId, variantId, quantity);
        item.setId(PrimaryKeyHolder.next(ORDER_ITEM_ID_VALUE_NAME));
        var product = this.productService.getProduct(productId).orElseThrow();
        var variant = product.getVariant(variantId).orElseThrow();
        item.setStoreId(product.getStoreId());
        item.setImageUrl(variant.getFirstImageUrl());
        item.setPrice(variant.getPrice());
        item.setOptionValues(List.copyOf(variant.getOptionValues()));
        item.setTitle(item.getTitle());
        return item;
    }

    @Override
    public Shipment createShipment(String orderId, List<String> itemIds) {
        var order = this.orderRepository.findById(orderId).orElseThrow();
        var shipment =
                new InternalShipment(orderId,
                        order.getBillingAddress(),
                        order.getShippingAddress(),
                        order.getItems(itemIds));
        shipment.setId(PrimaryKeyHolder.next(SHIPMENT_VALUE_NAME));
        return shipment;
    }

    @Override
    public void checkout(Order order) {

    }

    @Transactional
    public OrderCreation createOrders(List<InternalOrder> orders) throws CustomerValidException {
        customerValidator.validate(orders);
        checkoutCounter.checkout(orders);
        return new OrderCreation(
                this.orderRepository
                        .saveAll(this.orderSplitter.splitting(orders))
                        .stream().map(InternalOrder::getId).collect(Collectors.toList()),
                this.totalAmount(orders));
    }

    @Transactional
    public OrderCreation createOrder(InternalOrder order) throws CustomerValidException {
        return this.createOrders(List.of(order));
    }

    public BigDecimal totalAmount(PaymentOrder payOrder) {
        List<InternalOrder> orders = this.orderRepository.findAllById(payOrder.getOrders());
        return this.totalAmount(orders);
    }

    private BigDecimal totalAmount(List<InternalOrder> orders) {
        return orders.stream()
                .map(InternalOrder::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public PaymentLink createPaymentOrder(PaymentOrder payOrder) {
        List<InternalOrder> orders = this.orderRepository.findAllById(payOrder.getOrders());
        orders = Objects.isNull(orders) ? Collections.emptyList() : orders;
        Map<String, InternalOrder> orderMap = orders.stream().collect(Collectors.toMap(InternalOrder::getId, order -> order));
        for (String orderId : payOrder.getOrders()) {
            InternalOrder order = orderMap.get(orderId);
            if (Objects.isNull(order)) {
                throw new OrderException(String.format("The order(%s)  does not exist.", orderId));
            }
        }

        payOrder.setTotalAmount(this.totalAmount(orders));
        PaymentLink link = this.paymentService.createOrder(payOrder);

        // Set order payment details.
        for (InternalOrder order : orders) {
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
                        ifPresent(InternalOrder::paid);
            }
        });

    }

    public Optional<InternalOrder> getOrder(String orderId) {
        return this.orderRepository.findById(orderId);
    }

    public SliceList<InternalOrder> getOrders(OrderQuery query) {
        return this.orderRepository.findAll(query);
    }

    @Transactional
    @Override
    public Shipment addShipment(Shipment shipment) {
        InternalOrder order = this.orderRepository.findById(shipment.getOrderId()).orElseThrow();
        order.addShipment(shipment);
        return shipment;
    }

    @Transactional
    public void saveOrder(Order order) {
        this.orderRepository.save(InternalOrder.of(order));
    }
}
