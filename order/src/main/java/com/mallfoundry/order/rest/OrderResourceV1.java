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

package com.mallfoundry.order.rest;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.order.CustomerValidException;
import com.mallfoundry.order.InternalOrder;
import com.mallfoundry.order.InternalOrderService;
import com.mallfoundry.order.OrderCreation;
import com.mallfoundry.order.OrderQuery;
import com.mallfoundry.order.OrderStatus;
import com.mallfoundry.order.Shipment;
import com.mallfoundry.payment.PaymentException;
import com.mallfoundry.payment.PaymentLink;
import com.mallfoundry.payment.PaymentOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
public class OrderResourceV1 {

    private final InternalOrderService orderService;

    public OrderResourceV1(InternalOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/batch")
    public OrderCreation createOrders(@RequestBody List<InternalOrder> orders) throws CustomerValidException {
        return this.orderService.createOrders(orders);
    }

    @PostMapping("/orders")
    public OrderCreation createOrder(InternalOrder order) throws CustomerValidException {
        return this.orderService.createOrder(order);
    }

    @PostMapping("/orders/{order_id}/shipments")
    public Shipment addShipment(@PathVariable("order_id") String orderId,
                                @RequestBody ShipmentRequest request) {
        var shipment = this.orderService.createShipment(orderId, request.getItemIds());
        shipment.setShippingMethod(request.getShippingMethod());
        shipment.setShippingProvider(request.getShippingProvider());
        shipment.setTrackingNumber(request.getTrackingNumber());
        return this.orderService.addShipment(shipment);
    }

    @PutMapping("/orders/{order_id}/shipments/{shipment_id}")
    public void updateShipment(
            @PathVariable("order_id") String orderId,
            @PathVariable("shipment_id") String shipmentId,
            @RequestBody ShipmentRequest request) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        var shipment = order.getShipment(shipmentId).orElseThrow();
        if (StringUtils.isNotEmpty(request.getShippingMethod())) {
            shipment.setShippingMethod(request.getShippingMethod());
        }
        this.orderService.saveOrder(order);
    }

    @PostMapping("/orders/total_amount")
    public BigDecimal getTotalAmount(@RequestBody PaymentOrder order) {
        return this.orderService.totalAmount(order);
    }

    @PostMapping("/payment_orders")
    public PaymentLink createPaymentOrder(@RequestBody PaymentOrder order) throws PaymentException {
        return this.orderService.createPaymentOrder(order);
    }

    @GetMapping("/orders/{order_id}")
    public Optional<InternalOrder> getOrder(@PathVariable("order_id") String orderId) {
        return this.orderService.getOrder(orderId);
    }

    @GetMapping("/orders")
    public SliceList<InternalOrder> getOrders(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "customer_id", required = false) String customerId,
            @RequestParam(name = "status", required = false) List<String> statuses,
            @RequestParam(name = "store_id", required = false) String storeId) {
        return this.orderService.getOrders(OrderQuery.builder()
                .page(page).limit(limit)
                .customerId(customerId)
                // flat map -> filter is not empty -> upper case -> enum value of -> to list
                .statuses(() -> Stream.ofNullable(statuses)
                        .flatMap(List::stream)
                        .filter(StringUtils::isNotEmpty)
                        .map(StringUtils::upperCase)
                        .map(OrderStatus::valueOf)
                        .collect(Collectors.toList()))
                .storeId(storeId).build());
    }
}
