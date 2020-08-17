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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.configuration.Configuration;
import org.mallfoundry.configuration.ConfigurationHolder;
import org.mallfoundry.configuration.ConfigurationKeys;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.discuss.Author;
import org.mallfoundry.discuss.AuthorType;
import org.mallfoundry.discuss.DefaultAuthor;
import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentService;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.Subject;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

public class DefaultOrderService implements OrderService, OrderProcessorInvoker, ApplicationEventPublisherAware {

    private List<OrderProcessor> processors;

    private ApplicationEventPublisher eventPublisher;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    private final PaymentService paymentService;

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository,
                               OrderSplitter orderSplitter,
                               CarrierService carrierService,
                               PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
        this.paymentService = paymentService;
    }

    public void setProcessors(List<OrderProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new DefaultOrderQuery();
    }

    @Override
    public Order createOrder(String id) {
        return this.orderRepository.create(id);
    }

    @Override
    public OrderPayment createOrderPayment() {
        return new DelegatingOrderPayment(this.paymentService.createPayment(null));
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

    @Transactional
    @Override
    public List<Order> placeOrders(List<Order> orders) {
        orders = this.orderSplitter.splitting(orders);
        orders = this.invokePreProcessBeforePlaceOrders(orders);
//        Map<String, Configuration> configurations = new HashMap<>();
        var customerId = SubjectHolder.getSubject().getId();
        for (var order : orders) {
            var config = ConfigurationHolder.getConfiguration(order);
            order.setTenantId(config.getTenantId());
            order.setCustomerId(customerId);
            // 设置订单对象的扣减库存方式。
            // 当订单对象下单后或支付后，对订单所持有的库存判断其如何扣减或者归还。
            order.setInventoryDeduction(config.getEnum(ConfigurationKeys.ORDER_INVENTORY_DEDUCTION_KEY, InventoryDeduction.PLACED));
            order.place(config.getInt(ConfigurationKeys.ORDER_PLACING_EXPIRES_KEY));
        }
        orders = this.invokePreProcessAfterPlaceOrders(orders);
        orders = this.orderRepository.saveAll(orders);
        orders = this.invokePostProcessAfterPlaceOrders(orders);
        this.eventPublisher.publishEvent(new ImmutableOrdersPlacedEvent(orders));
        return orders;
    }

    private Order requiredOrder(String orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(OrderExceptions::notFound);
    }

    @Override
    public Order getOrder(String orderId) {
        return this.findOrder(orderId).orElseThrow(OrderExceptions::notFound);
    }

    @Override
    public Optional<Order> findOrder(String orderId) {
        return this.orderRepository.findById(orderId)
                .map(this::invokePostProcessAfterGetOrder);
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        try {
            query = this.invokePreProcessBeforeGetOrders(query);
            var orders = this.orderRepository.findAll(query);
            return this.invokePostProcessAfterGetOrders(orders);
        } finally {
            this.invokePostProcessAfterCompletion();
        }
    }

    @Override
    public long getOrderCount(OrderQuery query) {
        return this.orderRepository.count(query);
    }

    private void updateOrder(final Order source, final Order target) {
        if (isNotBlank(source.getStaffNotes())) {
            target.setStaffNotes(trim(source.getStaffNotes()));
        }
        if (nonNull(source.getStaffStars())) {
            target.setStaffStars(source.getStaffStars());
        }
    }

    @Transactional
    @Override
    public Order updateOrder(final Order source) {
        var order = this.requiredOrder(source.getId());
        order = this.invokePreProcessBeforeUpdateOrder(order);
        this.updateOrder(source, order);
        order = this.invokePreProcessAfterUpdateOrder(order);
        order = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderChangedEvent(order));
        return order;
    }

    @Transactional
    @Override
    public void payOrder(String orderId, OrderPaymentResult payment) {
        var order = this.requiredOrder(orderId);
        order.pay(payment);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderPaidEvent(savedOrder));
    }

    @Transactional
    @Override
    public void payOrders(Set<String> orderIds, OrderPaymentResult payment) {
        var orders = this.orderRepository.findAllById(orderIds);
        orders.forEach(order -> order.pay(payment));
        var savedOrders = this.orderRepository.saveAll(orders);
        this.eventPublisher.publishEvent(new ImmutableOrdersPaidEvent(savedOrders));
    }

    @Transactional
    @Override
    public void fulfilOrder(String orderId) {
        var order = this.invokePreProcessBeforeFulfilOrder(requiredOrder(orderId));
        order.fulfil();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderFulfilledEvent(savedOrder));
    }

    @Transactional
    @Override
    public void signOrder(String orderId, String message) {
        var order = this.requiredOrder(orderId);
        message = this.invokePreProcessBeforeSignOrder(order, message);
        order.sign(message);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderSignedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void receiptOrder(String orderId) {
        var order = this.requiredOrder(orderId);
        order = this.invokePreProcessBeforeReceiptOrder(order);
        order.receipt();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReceivedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId, String reason) {
        var order = requiredOrder(orderId);
        reason = this.invokePreProcessBeforeCancelOrder(order, reason);
        order.cancel(reason);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderCancelledEvent(savedOrder));
    }

    @Transactional
    @Override
    public void cancelOrders(Set<String> orderIds, String reason) {
        var orders = this.orderRepository.findAllById(orderIds);
        if (CollectionUtils.isNotEmpty(orders)) {
            String finalReason = this.invokePreProcessBeforeCancelOrders(orders, reason);
            orders.forEach(order -> order.cancel(finalReason));
            var savedOrders = this.orderRepository.saveAll(orders);
            this.eventPublisher.publishEvent(new ImmutableOrdersCancelledEvent(savedOrders));
        }
    }

    @Transactional
    @Override
    public OrderShipment addOrderShipment(String orderId, OrderShipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.invokePreProcessBeforeAddOrderShipment(order, shipment);
        shipment.setConsignorId(SubjectHolder.getSubject().getId());
        if (StringUtils.isBlank(shipment.getConsignor())) {
            shipment.setConsignor(SubjectHolder.getSubject().getNickname());
        }
        // Set the tracking carrier name.
        if (isNotBlank(shipment.getTrackingNumber())
                && StringUtils.isBlank(shipment.getTrackingCarrier())) {
            var carrier = this.carrierService.getCarrier(shipment.getShippingProvider()).orElseThrow();
            shipment.setTrackingCarrier(carrier.getName());
        }
        order.addShipment(shipment);
        return shipment;
    }

    @Override
    public Optional<OrderShipment> getOrderShipment(String orderId, String shipmentId) {
        var order = this.requiredOrder(orderId);
        return order.findShipment(shipmentId)
                .map(shipment -> this.invokePostProcessAfterGetOrderShipment(order, shipment));
    }

    @Override
    public List<OrderShipment> getOrderShipments(String orderId) {
        var order = this.requiredOrder(orderId);
        return this.invokePostProcessAfterGetOrderShipments(order, order.getShipments());
    }

    @Transactional
    @Override
    public void updateOrderShipment(String orderId, OrderShipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.invokePreProcessBeforeUpdateOrderShipment(order, shipment);
        order.updateShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void updateOrderShipments(String orderId, List<OrderShipment> shipments) {
        var order = this.requiredOrder(orderId);
        shipments = this.invokePreProcessBeforeUpdateOrderShipments(order, shipments);
        order.updateShipments(shipments);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeOrderShipment(String orderId, String shipmentId) {
        var order = this.requiredOrder(orderId);
        var shipment = order.getShipment(shipmentId);
        shipment = this.invokePreProcessBeforeRemoveOrderShipment(order, shipment);
        order.removeShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeOrderShipments(String orderId, Set<String> shipmentIds) {
        var order = this.requiredOrder(orderId);
        var shipments = order.getShipments(shipmentIds);
        shipments = this.invokePreProcessBeforeRemoveOrderShipments(order, shipments);
        order.removeShipments(shipments);
        this.orderRepository.save(order);
    }

    @Override
    public Payment startOrderPayment(OrderPayment orderPayment) throws OrderException {
        var payment = orderPayment.toPayment().toBuilder()
                .payerId(SubjectHolder.getSubject().getId()).payer(SubjectHolder.getSubject().getNickname()).build();
        var orders = this.orderRepository.findAllById(orderPayment.getOrderIds());
        String firstCustomerId = null;
        for (var order : orders) {
            if (!order.canPay()) {
                // 订单下单时间过期
                if (order.isPlacingExpired()) {
                    throw OrderExceptions.placingExpired();
                }
                throw OrderExceptions.notPay();
            }
            if (Objects.isNull(firstCustomerId)) {
                firstCustomerId = order.getCustomerId();
            }
            // 不允许一次支付不同顾客的订单
            if (!Objects.equals(order.getCustomerId(), firstCustomerId)) {
                throw OrderExceptions.notSameCustomer();
            }
            payment.addOrder(
                    payment.createOrder(order.getId())
                            .toBuilder()
                            .storeId(order.getStoreId()).amount(order.getTotalAmount())
                            .build());
        }
        return this.paymentService.startPayment(payment);
    }

    @Transactional
    @Override
    public OrderRefund applyOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.invokePreProcessBeforeApplyOrderRefund(order, refund);
        refund = order.applyRefund(refund);
        this.orderRepository.save(order);
        return refund;
    }

    @Transactional
    @Override
    public void cancelOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        var refund = this.invokePreProcessBeforeCancelOrderRefund(order, order.createRefund(refundId));
        order.cancelRefund(refund);
        this.orderRepository.save(order);
    }

    private void refundOrderPayment(Order order, String refundId, BigDecimal refundAmount) {
        var payRefund = this.paymentService.refundPayment(order.getPaymentId(),
                this.paymentService.createPayment(order.getPaymentId()).createRefund(refundId)
                        .toBuilder().orderId(order.getId()).amount(refundAmount).build());
        var refund = order.createRefund(refundId);
        if (payRefund.isSucceeded()) {
            order.succeedRefund(refund);
        } else if (payRefund.isFailed()) {
            order.failRefund(refund.toBuilder().failReason(payRefund.getFailReason()).build());
        }
    }

    @Transactional
    @Override
    public void approveOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        var refund = this.invokePreProcessBeforeApproveOrderRefund(order, order.createRefund(refundId));
        order.approveRefund(refund);
        this.refundOrderPayment(order, refund.getId(), refund.getTotalAmount());
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void disapproveOrderRefund(String orderId, String refundId, String disapprovedReason) {
        var order = this.requiredOrder(orderId);
        var refund = Function.<OrderRefund>identity()
                .<OrderRefund>compose(aRefund -> this.invokePreProcessBeforeDisapproveOrderRefund(order, aRefund))
                .<OrderRefund>compose(aRefund -> aRefund.toBuilder().disapprovalReason(disapprovedReason).build())
                .compose(order::createRefund)
                .apply(refundId);
        order.disapproveRefund(refund);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void activeOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.invokePreProcessBeforeActiveOrderRefund(order, refund);
        var fetchRefund = order.activeRefund(refund);
        this.refundOrderPayment(order, fetchRefund.getId(), fetchRefund.getTotalAmount());
        this.orderRepository.save(order);
    }

    @Override
    public Optional<OrderRefund> getOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        return order.getRefund(refundId)
                .map(refund -> this.invokePostProcessAfterGetOrderRefund(order, refund));
    }

    private Author createNewReviewer(Author author) {
        var newAuthor = new DefaultAuthor(SubjectHolder.getSubject().getId()).toBuilder()
                .type(AuthorType.CUSTOMER).nickname(SubjectHolder.getSubject().getNickname())
                .build();
        if (Objects.nonNull(author) && StringUtils.isNotBlank(author.getAvatar())) {
            newAuthor.setAvatar(author.getAvatar());
        }
        if (Objects.nonNull(author) && author.isAnonymous()) {
            newAuthor.anonymous();
        }
        return newAuthor;
    }

    @Transactional
    @Override
    public OrderReview addOrderReview(String orderId, OrderReview newReview) {
        var order = this.requiredOrder(orderId);
        var review = order.addReview(
                this.invokePreProcessBeforeAddOrderReview(order,
                        newReview.toBuilder()
                                .author(this.createNewReviewer(newReview.getAuthor()))
                                .build()));
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReviewedEvent(savedOrder, List.of(review)));
        return review;
    }

    @Transactional
    @Override
    public List<OrderReview> addOrderReviews(String orderId, List<OrderReview> newReviews) {
        var order = this.requiredOrder(orderId);
        newReviews.forEach(review -> review.toBuilder().author(this.createNewReviewer(review.getAuthor())).build());
        var reviews = order.addReviews(this.invokePreProcessBeforeAddOrderReviews(order, newReviews));
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReviewedEvent(savedOrder, reviews));
        return reviews;
    }


    @Override
    public List<Order> invokePreProcessBeforePlaceOrders(List<Order> orders) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessBeforePlaceOrders)
                .apply(orders);
    }

    @Override
    public List<Order> invokePreProcessAfterPlaceOrders(List<Order> orders) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessAfterPlaceOrders)
                .apply(orders);
    }

    @Override
    public List<Order> invokePostProcessAfterPlaceOrders(List<Order> orders) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::postProcessAfterPlaceOrders)
                .apply(orders);
    }

    @Override
    public Order invokePostProcessAfterGetOrder(Order order) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::postProcessAfterGetOrder)
                .apply(order);
    }

    @Override
    public OrderQuery invokePreProcessBeforeGetOrders(OrderQuery query) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessBeforeGetOrders)
                .apply(query);
    }

    @Override
    public SliceList<Order> invokePostProcessAfterGetOrders(SliceList<Order> orders) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::postProcessAfterGetOrders)
                .apply(orders);
    }

    @Override
    public Order invokePreProcessBeforeUpdateOrder(Order order) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessBeforeUpdateOrder)
                .apply(order);
    }

    @Override
    public Order invokePreProcessBeforeFulfilOrder(Order order) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessBeforeFulfilOrder)
                .apply(order);
    }

    @Override
    public Order invokePreProcessAfterUpdateOrder(Order order) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessAfterUpdateOrder)
                .apply(order);
    }

    @Override
    public String invokePreProcessBeforeSignOrder(Order order, String message) {
        return Processors.stream(this.processors)
                .<String>map((processor, identity) -> processor.preProcessBeforeSignOrder(order, identity))
                .apply(message);
    }

    @Override
    public Order invokePreProcessBeforeReceiptOrder(Order order) {
        return Processors.stream(this.processors)
                .map(OrderProcessor::preProcessBeforeReceiptOrder)
                .apply(order);
    }

    @Override
    public String invokePreProcessBeforeCancelOrder(Order order, String reason) {
        return Processors.stream(this.processors)
                .<String>map((processor, identity) -> processor.preProcessBeforeCancelOrder(order, identity))
                .apply(reason);
    }

    @Override
    public String invokePreProcessBeforeCancelOrders(List<Order> orders, String reason) {
        return Processors.stream(this.processors)
                .<String>map((processor, identity) -> processor.preProcessBeforeCancelOrders(orders, identity))
                .apply(reason);
    }

    @Override
    public OrderShipment invokePreProcessBeforeAddOrderShipment(Order order, OrderShipment shipment) {
        return Processors.stream(this.processors)
                .<OrderShipment>map((processor, identity) -> processor.preProcessBeforeAddOrderShipment(order, identity))
                .apply(shipment);
    }

    @Override
    public OrderShipment invokePostProcessAfterGetOrderShipment(Order order, OrderShipment shipment) {
        return Processors.stream(this.processors)
                .<OrderShipment>map((processor, identity) -> processor.postProcessAfterGetOrderShipment(order, identity))
                .apply(shipment);
    }

    @Override
    public List<OrderShipment> invokePostProcessAfterGetOrderShipments(Order order, List<OrderShipment> shipments) {
        return Processors.stream(this.processors)
                .<List<OrderShipment>>map((processor, identity) -> processor.postProcessAfterGetOrderShipments(order, identity))
                .apply(shipments);
    }

    @Override
    public OrderShipment invokePreProcessBeforeUpdateOrderShipment(Order order, OrderShipment shipment) {
        return Processors.stream(this.processors)
                .<OrderShipment>map((processor, identity) -> processor.preProcessBeforeUpdateOrderShipment(order, identity))
                .apply(shipment);
    }

    @Override
    public List<OrderShipment> invokePreProcessBeforeUpdateOrderShipments(Order order, List<OrderShipment> shipments) {
        return Processors.stream(this.processors)
                .<List<OrderShipment>>map((processor, identity) -> processor.preProcessBeforeUpdateOrderShipments(order, identity))
                .apply(shipments);
    }

    @Override
    public OrderShipment invokePreProcessBeforeRemoveOrderShipment(Order order, OrderShipment shipment) {
        return Processors.stream(this.processors)
                .<OrderShipment>map((processor, identity) -> processor.preProcessBeforeRemoveOrderShipment(order, identity))
                .apply(shipment);
    }

    @Override
    public List<OrderShipment> invokePreProcessBeforeRemoveOrderShipments(Order order, List<OrderShipment> shipments) {
        return Processors.stream(this.processors)
                .<List<OrderShipment>>map((processor, identity) -> processor.preProcessBeforeRemoveOrderShipments(order, identity))
                .apply(shipments);
    }

    @Override
    public OrderRefund invokePreProcessBeforeApplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeApplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeCancelOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeApplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeApproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeApproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeDisapproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeDisapproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeActiveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeActiveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterGetOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterGetOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderReview invokePreProcessBeforeAddOrderReview(Order order, OrderReview review) {
        return Processors.stream(this.processors)
                .<OrderReview>map((processor, identity) -> processor.preProcessBeforeAddOrderReview(order, identity))
                .apply(review);
    }

    @Override
    public List<OrderReview> invokePreProcessBeforeAddOrderReviews(Order order, List<OrderReview> reviews) {
        return Processors.stream(this.processors)
                .<List<OrderReview>>map((processor, identity) -> processor.preProcessBeforeAddOrderReviews(order, identity))
                .apply(reviews);
    }

    @Override
    public void invokePostProcessAfterCompletion() {
        this.processors.forEach(OrderProcessor::postProcessAfterCompletion);
    }
}
