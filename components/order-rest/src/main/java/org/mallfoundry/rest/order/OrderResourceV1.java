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

package org.mallfoundry.rest.order;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderRating;
import org.mallfoundry.order.OrderReview;
import org.mallfoundry.order.OrderService;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.order.OrderType;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentMethod;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class OrderResourceV1 {

    private final OrderService orderService;

    public OrderResourceV1(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/orders/{order_id}")
    public Order updateOrder(@PathVariable("order_id") String orderId, @RequestBody OrderRequest request) {
        var order = this.orderService.createOrder(orderId);
        return this.orderService.updateOrder(request.assignTo(order));
    }

    @PostMapping("/orders/{order_id}/cancel")
    public void cancelOrder(@PathVariable("order_id") String orderId,
                            @RequestBody OrderRequest.CancelRequest request) {
        this.orderService.cancelOrder(orderId, request.getCancelReason());
    }

    @PostMapping("/orders/{order_id}/decline")
    public void declineOrder(@PathVariable("order_id") String orderId,
                             @RequestBody OrderRequest.DeclineRequest request) {
        this.orderService.declineOrder(orderId, request.getDeclineReason());
    }

    @PostMapping("/orders/{order_id}/sign")
    public void signOrder(@PathVariable("order_id") String orderId,
                          @RequestBody OrderRequest.SignRequest request) {
        this.orderService.signOrder(orderId, request.getSignMessage());
    }

    @PostMapping("/orders/{order_id}/receipt")
    public void receiptOrder(@PathVariable("order_id") String orderId) {
        this.orderService.receiptOrder(orderId);
    }

    @GetMapping("/orders/{order_id}")
    public Optional<Order> getOrder(@PathVariable("order_id") String orderId) {
        return this.orderService.findOrder(orderId);
    }

    @GetMapping("/orders")
    public SliceList<Order> getOrders(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                      @RequestParam(name = "customer_id", required = false) String customerId,
                                      @RequestParam(name = "store_id", required = false) String storeId,
                                      @RequestParam(name = "ids", required = false) Set<String> ids,
                                      @RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "statuses", required = false) Set<String> statuses,
                                      @RequestParam(name = "dispute_statuses", required = false) Set<String> disputeStatuses,
                                      @RequestParam(name = "review_statuses", required = false) Set<String> reviewStatuses,
                                      @RequestParam(name = "types", required = false) Set<String> types,
                                      @RequestParam(name = "sources", required = false) Set<String> sources,
                                      @RequestParam(name = "payment_methods", required = false) Set<String> paymentMethods,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      @RequestParam(name = "placed_time_min", required = false) Date placeTimeMin,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      @RequestParam(name = "placed_time_max", required = false) Date placedTimeMax,
                                      @RequestParam(name = "sort", required = false) String sort) {
        return this.orderService.getOrders(this.orderService.createOrderQuery().toBuilder()
                .page(page).limit(limit).sort(aSort -> aSort.from(sort))
                .customerId(customerId).storeId(storeId)
                .ids(ids).name(name)
                .statuses(() ->
                        CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .disputeStatuses(() ->
                        CollectionUtils.emptyIfNull(disputeStatuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .reviewStatuses(() ->
                        CollectionUtils.emptyIfNull(reviewStatuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .types(() ->
                        CollectionUtils.emptyIfNull(types).stream().map(StringUtils::upperCase)
                                .map(OrderType::valueOf).collect(Collectors.toUnmodifiableSet()))
                .sources(() ->
                        CollectionUtils.emptyIfNull(sources).stream().map(StringUtils::upperCase)
                                .map(OrderSource::valueOf).collect(Collectors.toUnmodifiableSet()))
                .paymentMethods(() ->
                        CollectionUtils.emptyIfNull(paymentMethods).stream().map(StringUtils::upperCase)
                                .map(PaymentMethod::valueOf).collect(Collectors.toUnmodifiableSet()))
                .placedTimeMin(placeTimeMin).placedTimeMax(placedTimeMax)
                .build());
    }

    @GetMapping("/orders/count")
    public long countOrders(@RequestParam(name = "store_id", required = false) String storeId,
                            @RequestParam(name = "customer_id", required = false) String customerId,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "statuses", required = false) Set<String> statuses,
                            @RequestParam(name = "dispute_statuses", required = false) Set<String> disputeStatuses,
                            @RequestParam(name = "review_statuses", required = false) Set<String> reviewStatuses) {
        return this.orderService.countOrders(this.orderService.createOrderQuery().toBuilder()
                .customerId(customerId).storeId(storeId)
                .name(name)
                .statuses(() ->
                        CollectionUtils.emptyIfNull(statuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .disputeStatuses(() ->
                        CollectionUtils.emptyIfNull(disputeStatuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .reviewStatuses(() ->
                        CollectionUtils.emptyIfNull(reviewStatuses).stream().map(StringUtils::upperCase)
                                .map(OrderStatus::valueOf).collect(Collectors.toUnmodifiableSet()))
                .build());
    }

    @PostMapping("/orders/{order_id}/shipments")
    public OrderShipment addOrderShipment(@PathVariable("order_id") String orderId,
                                          @RequestBody OrderShipmentRequest.AddOrderShipmentRequest request) {
        var order = this.orderService.createOrder(orderId);
        return Function.<OrderShipment>identity()
                .<OrderShipment>compose(shipment -> this.orderService.addOrderShipment(orderId, shipment))
                .compose(request::assignTo)
                .apply(order.createShipment(null));
    }

    @GetMapping("/orders/{order_id}/shipments")
    public List<OrderShipment> getOrderShipments(@PathVariable("order_id") String orderId) {
        return this.orderService.getOrderShipments(orderId);
    }

    @GetMapping("/orders/{order_id}/shipments/{shipment_id}")
    public Optional<OrderShipment> findOrderShipment(@PathVariable("order_id") String orderId,
                                                     @PathVariable("shipment_id") String shipmentId) {
        return this.orderService.findOrderShipment(orderId, shipmentId);
    }

    @PatchMapping("/orders/{order_id}/shipments/{shipment_id}")
    public void updateOrderShipment(@PathVariable("order_id") String orderId,
                                    @PathVariable("shipment_id") String shipmentId,
                                    @RequestBody OrderShipmentRequest request) {
        this.orderService.updateOrderShipment(orderId,
                request.assignTo(
                        this.orderService.createOrder(orderId).createShipment(shipmentId)));
    }

    @PatchMapping("/orders/{order_id}/shipments/batch")
    public void updateOrderShipments(@PathVariable("order_id") String orderId,
                                     @RequestBody List<BatchShipmentRequest> requests) {
        var order = this.orderService.createOrder(orderId);
        var shipments = requests.stream()
                .map(request ->
                        request.assignTo(order.createShipment(request.getId())))
                .collect(Collectors.toList());
        this.orderService.updateOrderShipments(orderId, shipments);
    }

    @DeleteMapping("/orders/{order_id}/shipments/{shipment_id}")
    public void removeOrderShipment(@PathVariable("order_id") String orderId,
                                    @PathVariable("shipment_id") String shipmentId) {
        this.orderService.removeOrderShipment(orderId, shipmentId);
    }

    @PostMapping("/orders/payments")
    public Payment startOrderPayment(@RequestBody OrderPaymentRequest request) {
        return Function.<Payment>identity()
                .compose(this.orderService::startOrderPayment)
                .compose(request::assignTo)
                .apply(this.orderService.createOrderPayment());
    }

    @PostMapping("/orders/{order_id}/refunds")
    public OrderRefund applyOrderRefund(@PathVariable("order_id") String orderId,
                                        @RequestBody OrderRefundRequest request) {
        var refund = request.assignTo(this.orderService.createOrder(orderId).createRefund(null));
        return this.orderService.applyOrderRefund(orderId, refund);
    }

    @PostMapping("/orders/{order_id}/refunds/batch")
    public List<OrderRefund> applyOrderRefunds(@PathVariable("order_id") String orderId,
                                               @RequestBody List<OrderRefundRequest> requests) {
        var order = this.orderService.createOrder(orderId);
        var refunds = requests.stream().map(request -> request.assignTo(order.createRefund(null)))
                .collect(Collectors.toUnmodifiableList());
        return this.orderService.applyOrderRefunds(orderId, refunds);
    }

    @DeleteMapping("/orders/{order_id}/refunds/{refund_id}/cancel")
    public void cancelOrderRefund(@PathVariable("order_id") String orderId,
                                  @PathVariable("refund_id") String refundId) {
        this.orderService.cancelOrderRefund(orderId, refundId);
    }

    @PatchMapping("/orders/{order_id}/refunds/{refund_id}/approve")
    public void approveOrderRefund(@PathVariable("order_id") String orderId,
                                   @PathVariable("refund_id") String refundId) {
        this.orderService.approveOrderRefund(orderId, refundId);
    }

    @PatchMapping("/orders/{order_id}/refunds/{refund_id}/disapprove")
    public void disapproveOrderRefund(@PathVariable("order_id") String orderId,
                                      @PathVariable("refund_id") String refundId,
                                      @RequestBody OrderRefundRequest.Disapprove request) {
        this.orderService.disapproveOrderRefund(orderId, refundId, request.getDisapprovalReason());
    }

    @PatchMapping("/orders/{order_id}/refunds/{refund_id}/reapply")
    public OrderRefund reapplyOrderRefund(@PathVariable("order_id") String orderId,
                                          @PathVariable("refund_id") String refundId,
                                          @RequestBody OrderRefundRequest.Reapply request) {
        var refund = request.assignTo(this.orderService.createOrder(orderId).createRefund(refundId));
        return this.orderService.reapplyOrderRefund(orderId, refund);
    }

    @PostMapping("/orders/{order_id}/refunds/active")
    public void activeOrderRefund(@PathVariable("order_id") String orderId,
                                  @RequestBody OrderRefundRequest request) {
        var refund = request.assignTo(this.orderService.createOrder(orderId).createRefund(null));
        this.orderService.activeOrderRefund(orderId, refund);
    }

    @GetMapping("/orders/{order_id}/refunds/{refund_id}")
    public Optional<OrderRefund> findOrderRefund(@PathVariable("order_id") String orderId,
                                                 @PathVariable("refund_id") String refundId) {
        return this.orderService.findOrderRefund(orderId, refundId);
    }

    @PostMapping("/orders/{order_id}/reviews/batch")
    public List<OrderReview> reviewOrder(@PathVariable("order_id") String orderId,
                                         @RequestBody List<OrderReviewRequest> requests) {
        var order = this.orderService.createOrder(orderId);
        var reviews = requests.stream()
                .map(r -> r.assignTo(order.createReview(null)))
                .collect(Collectors.toUnmodifiableList());
        return this.orderService.reviewOrder(orderId, reviews);
    }

    @GetMapping("/orders/{order_id}/ratings")
    public List<OrderRating> getOrderRatings(@PathVariable("order_id") String orderId) {
        return this.orderService.getOrderRatings(orderId);
    }

    @PostMapping("/orders/{order_id}/ratings/batch")
    public void ratingOrder(@PathVariable("order_id") String orderId,
                            @RequestBody List<OrderRatingRequest> requests) {
        var order = this.orderService.createOrder(orderId);
        var ratings = requests.stream()
                .map(r -> r.assignTo(order.createRating(r.getType())))
                .collect(Collectors.toUnmodifiableList());
        this.orderService.ratingOrder(orderId, ratings);
    }
}
