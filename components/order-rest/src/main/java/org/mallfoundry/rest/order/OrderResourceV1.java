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

package org.mallfoundry.rest.order;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.order.Shipment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
public class OrderResourceV1 {

    private final OrderService orderService;

    public OrderResourceV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/orders/{order_id}")
    public Order updateOrder(@PathVariable("order_id") String orderId, @RequestBody OrderRequest request) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        if (Objects.nonNull(request.getStaffNotes())) {
            order.setStaffNotes(request.getStaffNotes());
        }

        if (request.isDiscountAmountsChanged()) {
            order.discounts(request.getDiscountAmounts());
        }

        if (request.isDiscountShippingCostsChanged()) {
            order.discountShippingCosts(request.getDiscountShippingCosts());
        }
        return this.orderService.updateOrder(order);
    }

    @PostMapping("/orders/{order_id}/cancel")
    public void cancelOrder(@PathVariable("order_id") String orderId,
                            @RequestBody OrderRequest.CancelRequest request) {
        this.orderService.cancelOrder(orderId, request.getReason());
    }

    @GetMapping("/orders/{order_id}")
    public Optional<Order> getOrder(@PathVariable("order_id") String orderId) {
        return this.orderService.getOrder(orderId);
    }

    @GetMapping("/orders")
    public SliceList<Order> getOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                      @RequestParam(name = "customer_id", required = false) String customerId,
                                      @RequestParam(name = "statuses", required = false) List<String> statuses,
                                      @RequestParam(name = "store_id", required = false) String storeId) {
        return this.orderService.getOrders(this.orderService.createOrderQuery().toBuilder()
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

    @PostMapping("/orders/{order_id}/shipments")
    public Shipment addShipment(@PathVariable("order_id") String orderId,
                                @RequestBody AddShipmentRequest request) {
        var itemIds = request.getItems().stream()
                .map(OrderRequest.OrderItemRequest::getId)
                .collect(Collectors.toList());
        var shipment = this.orderService.createShipment(orderId, itemIds).toBuilder()
                .shippingProvider(request.getShippingProvider())
                .shippingMethod(request.getShippingMethod())
                .trackingNumber(request.getTrackingNumber())
                .build();
        return this.orderService.addShipment(orderId, shipment);
    }

    @PostMapping("/orders/{order_id}/shipments/{shipment_id}")
    public Optional<Shipment> getShipment(@PathVariable("order_id") String orderId,
                                          @PathVariable("shipment_id") String shipmentId) {
        return this.orderService.getShipment(orderId, shipmentId);
    }

    public void requestToShipment(ShipmentRequest request, Shipment shipment) {
        if (StringUtils.isNotEmpty(request.getShippingMethod())) {
            shipment.setShippingMethod(request.getShippingMethod());
        }

        if (StringUtils.isNotEmpty(request.getShippingProvider())) {
            shipment.setShippingProvider(request.getShippingProvider());
        }

        if (StringUtils.isNotEmpty(request.getTrackingNumber())) {
            shipment.setTrackingNumber(request.getTrackingNumber());
        }
    }

    @PatchMapping("/orders/{order_id}/shipments/{shipment_id}")
    public void setShipment(@PathVariable("order_id") String orderId,
                            @PathVariable("shipment_id") String shipmentId,
                            @RequestBody ShipmentRequest request) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        var shipment = order.createShipment(shipmentId);
        this.requestToShipment(request, shipment);
        this.orderService.setShipment(orderId, shipment);
    }

    @PatchMapping("/orders/{order_id}/shipments/batch")
    public void setShipments(@PathVariable("order_id") String orderId,
                             @RequestBody List<BatchShipmentRequest> requests) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        var shipments = requests.stream().map(request -> {
            var shipment = order.createShipment(request.getId());
            this.requestToShipment(request, shipment);
            return shipment;
        }).collect(Collectors.toList());
        this.orderService.setShipments(orderId, shipments);
    }

    @DeleteMapping("/orders/{order_id}/shipments/{shipment_id}")
    public void removeShipment(@PathVariable("order_id") String orderId,
                               @PathVariable("shipment_id") String shipmentId) {
        this.orderService.removeShipment(orderId, shipmentId);
    }
}
