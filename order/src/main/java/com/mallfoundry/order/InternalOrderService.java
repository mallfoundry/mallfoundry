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
import org.springframework.data.util.CastUtils;
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
    public ShippingAddress createShippingAddress() {
        return new InternalShippingAddress();
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new InternalOrderQuery();
    }

    @Override
    public Order createOrder(ShippingAddress shippingAddress, List<OrderItem> items) {
        var order = new InternalOrder(shippingAddress, items);
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
        var shipment = new InternalShipment(orderId, order.getItems(itemIds));
        shipment.setId(PrimaryKeyHolder.next(SHIPMENT_VALUE_NAME));
        shipment.setConsignorId(SecurityUserHolder.getUserId());
        shipment.setConsignor(SecurityUserHolder.getNickname());
        shipment.setShippingAddress(order.getShippingAddress());
        return shipment;
    }

    @Transactional
    @Override
    public List<Order> checkout(Order order) {
        return this.checkout(List.of(order));
    }

    @Transactional
    @Override
    public List<Order> checkout(List<Order> orders) throws CustomerValidException {
        customerValidator.validate(orders);
        checkoutCounter.checkout(orders);
        var splitOrders = this.orderSplitter.splitting(orders)
                .stream().map(InternalOrder::of).collect(Collectors.toList());
        return CastUtils.cast(this.orderRepository.saveAll(splitOrders));
    }

    private BigDecimal totalAmount(List<InternalOrder> orders) {
        return orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public PaymentLink createPaymentOrder(PaymentOrder payOrder) {
        List<InternalOrder> orders = this.orderRepository.findAllById(payOrder.getOrders());
        orders = Objects.isNull(orders) ? Collections.emptyList() : orders;
        Map<String, Order> orderMap = orders.stream().collect(Collectors.toMap(Order::getId, order -> order));
        for (String orderId : payOrder.getOrders()) {
            Order order = orderMap.get(orderId);
            if (Objects.isNull(order)) {
                throw new OrderException(String.format("The order(%s)  does not exist.", orderId));
            }
        }

        payOrder.setTotalAmount(this.totalAmount(orders));
        PaymentLink link = this.paymentService.createOrder(payOrder);

        // Set order payment details.
        for (var order : orders) {
            order.awaitingPayment(new InternalPaymentDetails(link.getId(), payOrder.getProvider(), payOrder.getStatus()));
        }

        return link;
    }

    @Transactional
    public void confirmPayment(PaymentOrder paymentOrder) {
        paymentOrder.getOrders()
                .forEach(orderId ->
                        this.orderRepository.findById(orderId).ifPresent(order ->
                                order.confirmPayment(
                                        new InternalPaymentDetails(
                                                paymentOrder.getId(),
                                                paymentOrder.getProvider(),
                                                paymentOrder.getStatus()))));
    }

    @Transactional
    @Override
    public Shipment addShipment(Shipment shipment) {
        var order = this.orderRepository.findById(shipment.getOrderId()).orElseThrow();
        order.addShipment(shipment);
        return shipment;
    }

    @Transactional
    public void saveOrder(Order order) {
        this.orderRepository.save(InternalOrder.of(order));
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return CastUtils.cast(this.orderRepository.findById(orderId));
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        return CastUtils.cast(this.orderRepository.findAll(query));
    }

    @Override
    public void changeDiscountAmount(String orderId, Map<String, BigDecimal> amounts) {
        var order = this.orderRepository.findById(orderId).orElseThrow();

//        amounts.forEach(itemDiscount -> {
//            order.getItems()
//        });

    }

    @Transactional
    @Override
    public void cancelOrder(String orderId) {
        this.orderRepository.findById(orderId)
                .ifPresent(InternalOrder::cancel);
    }
}
