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

package com.mallfoundry.rest.order;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.order.Order;
import com.mallfoundry.order.OrderItem;
import com.mallfoundry.order.OrderService;
import com.mallfoundry.order.OrderStatus;
import com.mallfoundry.order.Shipment;
import com.mallfoundry.order.ShippingAddress;
import org.apache.commons.lang3.StringUtils;
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

    private ShippingAddress createShippingAddress(CreateOrderRequest.ShippingAddressRequest request) {
        return Objects.isNull(request) ? null :
                this.orderService.createShippingAddress().toBuilder()
                        .countryCode(request.getCountryCode()).zip(request.getZip())
                        .firstName(request.getFirstName()).lastName(request.getLastName())
                        .mobile(request.getMobile())
                        .address(request.getAddress()).location(request.getLocation()).build();
    }

    private OrderItem createOrderItem(CreateOrderRequest.OrderItemRequest request) {
        return this.orderService.createOrderItem(request.getProductId(), request.getVariantId(), request.getQuantity());
    }

    private List<OrderItem> createOrderItems(List<CreateOrderRequest.OrderItemRequest> requests) {
        return requests.stream().map(this::createOrderItem).collect(Collectors.toList());
    }

    private Order createOrder(CreateOrderRequest request) {
        return this.orderService.createOrder(
                createShippingAddress(request.getShippingAddress()),
                createOrderItems(request.getItems()));
    }

    private List<Order> createOrders(List<CreateOrderRequest> requests) {
        return requests.stream().map(this::createOrder).collect(Collectors.toList());
    }

    @PostMapping("/orders/batch")
    public List<Order> checkout(@RequestBody List<CreateOrderRequest> request) {
        return this.orderService.checkout(this.createOrders(request));
    }


    @PostMapping("/orders")
    public List<Order> checkout(@RequestBody CreateOrderRequest request) {
        return this.orderService.checkout(this.createOrder(request));
    }

    @PostMapping("/orders/{order_id}/shipments")
    public Shipment addShipment(@PathVariable("order_id") String orderId,
                                @RequestBody AddShipmentRequest request) {
        var shipment = this.orderService.createShipment(orderId, request.getItemIds());
        shipment.setShippingMethod(request.getShippingMethod());
        shipment.setShippingProvider(request.getShippingProvider());
        shipment.setTrackingNumber(request.getTrackingNumber());
        return this.orderService.addShipment(shipment);
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
    public void patchShipment(@PathVariable("order_id") String orderId,
                              @PathVariable("shipment_id") String shipmentId,
                              @RequestBody ShipmentRequest request) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        var shipment = order.getShipment(shipmentId).orElseThrow();
        this.requestToShipment(request, shipment);
        this.orderService.checkout(order);
    }

    @PatchMapping("/orders/{order_id}/shipments/batch")
    public void patchShipments(@PathVariable("order_id") String orderId,
                               @RequestBody List<BatchShipmentRequest> requests) {
        var order = this.orderService.getOrder(orderId).orElseThrow();
        for (var request : requests) {
            var shipment = order.getShipment(request.getId()).orElseThrow();
            this.requestToShipment(request, shipment);
        }
        this.orderService.checkout(order);
    }

    @PatchMapping("/orders/{order_id}")
    public void patchOrder(@PathVariable("order_id") String orderId,
                           @RequestBody OrderRequest request) {
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
        this.orderService.checkout(order);
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
    public SliceList<Order> getOrders(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "customer_id", required = false) String customerId,
            @RequestParam(name = "status", required = false) List<String> statuses,
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
}
