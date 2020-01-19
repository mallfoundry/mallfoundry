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

import com.mallfoundry.order.CustomerValidException;
import com.mallfoundry.order.Order;
import com.mallfoundry.order.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class OrderResourceV1 {

    private final OrderService orderService;

    public OrderResourceV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public Order submitOrder(Order order) throws CustomerValidException {
        return this.orderService.submitOrder(order);
    }

    @PatchMapping("/orders/{order_id}/awaiting_payment")
    public void awaitingPayment(@PathVariable("order_id") Long orderId) {
        this.orderService.awaitingPayment(orderId);
    }

    @PatchMapping("/orders/{order_id}/awaiting_fulfillment")
    public void awaitingFulfillment(
            @PathVariable("order_id") Long orderId) {
        this.orderService.awaitingFulfillment(orderId);
    }

    @PatchMapping("/orders/{order_id}/awaiting_shipment")
    public void awaitingShipment(@PathVariable("order_id") Long orderId) {
        this.orderService.awaitingShipment(orderId);
    }

    @PatchMapping("/orders/{order_id}/partially_shipped")
    public void partiallyShipped(@PathVariable("order_id") Long orderId,
                                 @RequestBody ShippedRequest request) {
        this.orderService.partiallyShipped(orderId, request.getShipments());
    }

    @PatchMapping("/orders/{order_id}/shipped")
    public void shipped(@PathVariable("order_id") Long orderId,
                        @RequestBody ShippedRequest request) {
        this.orderService.shipped(orderId, request.getShipments());
    }

    @PatchMapping("/orders/{order_id}/awaiting_pickup")
    public void awaitingPickup(@PathVariable("order_id") Long orderId) {
        this.orderService.awaitingPickup(orderId);
    }

    @PatchMapping("/orders/{order_id}/completed")
    public void completed(@PathVariable("order_id") Long orderId) {
        this.orderService.completed(orderId);
    }

    @PatchMapping("/orders/{order_id}/disputed")
    public void disputed(@PathVariable("order_id") Long orderId) {
        this.orderService.disputed(orderId);
    }

    @PatchMapping("/orders/{order_id}/refunded")
    public void refunded(@PathVariable("order_id") Long orderId) {
        this.orderService.refunded(orderId);
    }

    @PatchMapping("/orders/{order_id}/cancelled")
    public void cancelled(@PathVariable("order_id") Long orderId) {
        this.orderService.cancelled(orderId);
    }

    @PatchMapping("/orders/{order_id}/declined")
    public void declined(@PathVariable("order_id") Long orderId) {
        this.orderService.declined(orderId);
    }

    @GetMapping("/orders/{order_id}")
    public Optional<Order> createOrder(@PathVariable("order_id") Long orderId) {
        return this.orderService.getOrder(orderId);
    }
}
