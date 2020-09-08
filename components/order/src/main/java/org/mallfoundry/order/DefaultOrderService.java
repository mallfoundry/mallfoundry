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
import org.mallfoundry.config.ConfigurationHolder;
import org.mallfoundry.config.ConfigurationKeys;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.discuss.Author;
import org.mallfoundry.discuss.AuthorType;
import org.mallfoundry.discuss.DefaultAuthor;
import org.mallfoundry.inventory.InventoryDeduction;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentService;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DefaultOrderService implements OrderService, OrderProcessorInvoker, ApplicationEventPublisherAware {

    private List<OrderProcessor> processors;

    private ApplicationEventPublisher eventPublisher;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    private final PaymentService paymentService;

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderSplitter orderSplitter,
                               CarrierService carrierService,
                               PaymentService paymentService,
                               OrderRepository orderRepository) {
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
        this.paymentService = paymentService;
        this.orderRepository = orderRepository;
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
        var customerId = SubjectHolder.getSubject().getId();
        for (var order : orders) {
            var config = ConfigurationHolder.getConfiguration(order);
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
    public long countOrders(OrderQuery query) {
        return this.orderRepository.count(query);
    }

    private void updateOrder(final Order source, final Order target) {
        Copies.notBlank(source::getStaffNotes).trim(target::setStaffNotes);
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
    public void closeOrders(Set<String> orderIds, String closeReason) {
        var orders = this.orderRepository.findAllById(orderIds);
        if (CollectionUtils.isNotEmpty(orders)) {
            String finalReason = this.invokePreProcessBeforeCloseOrders(orders, closeReason);
            orders.forEach(order -> order.close(finalReason));
            var savedOrders = this.orderRepository.saveAll(orders);
            this.eventPublisher.publishEvent(new ImmutableOrdersClosedEvent(savedOrders));
        }
    }

    @Override
    public void declineOrder(String orderId, String declineReason) {
        var order = requiredOrder(orderId);
        declineReason = this.invokePreProcessBeforeDeclineOrder(order, declineReason);
        order.decline(declineReason);
        order = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderDeclinedEvent(order));
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
        if (isNotBlank(shipment.getTrackingNumber()) && StringUtils.isBlank(shipment.getTrackingCarrier())) {
            var carrier = this.carrierService.getCarrier(shipment.getShippingProvider());
            shipment.setTrackingCarrier(carrier.getName());
        }
        order.addShipment(shipment);
        return shipment;
    }

    @Override
    public Optional<OrderShipment> findOrderShipment(String orderId, String shipmentId) {
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

    private void setRefundApplicant(OrderRefund refund) {
        refund.setApplicant(SubjectHolder.getSubject().getNickname());
        refund.setApplicantId(SubjectHolder.getSubject().getId());
    }

    @Transactional
    @Override
    public OrderRefund applyOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.invokePreProcessBeforeApplyOrderRefund(order, refund);
        var configuration = ConfigurationHolder.getConfiguration(order);
        // 设置申请超时时间。
        refund.setApplyingExpires(configuration.getInt(ConfigurationKeys.ORDER_DISPUTE_APPLYING_EXPIRES_KEY));
        this.setRefundApplicant(refund);
        refund = order.applyRefund(refund);
        refund = this.invokePreProcessAfterApplyOrderRefund(order, refund);
        this.orderRepository.save(order);
        this.invokePostProcessAfterApplyOrderRefund(order, refund);
        return refund;
    }

    @Override
    public List<OrderRefund> applyOrderRefunds(String orderId, List<OrderRefund> refunds) {
        var order = this.requiredOrder(orderId);
        refunds = this.invokePreProcessBeforeApplyOrderRefunds(order, refunds);
        var configuration = ConfigurationHolder.getConfiguration(order);
        refunds.forEach(refund -> {
            // 设置申请超时时间。
            refund.setApplyingExpires(configuration.getInt(ConfigurationKeys.ORDER_DISPUTE_APPLYING_EXPIRES_KEY));
            this.setRefundApplicant(refund);
        });
        refunds = order.applyRefunds(refunds);
        refunds = this.invokePreProcessAfterApplyOrderRefunds(order, refunds);
        this.orderRepository.save(order);
        refunds = this.invokePostProcessAfterApplyOrderRefunds(order, refunds);
        return refunds;
    }

    @Transactional
    @Override
    public void cancelOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        var refund = this.invokePreProcessBeforeCancelOrderRefund(order, order.createRefund(refundId));
        refund = order.cancelRefund(refund);
        refund = this.invokePreProcessAfterCancelOrderRefund(order, refund);
        this.orderRepository.save(order);
        this.invokePostProcessAfterCancelOrderRefund(order, refund);
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
        refund = order.approveRefund(refund);
        this.refundOrderPayment(order, refund.getId(), refund.getAmount());
        refund = this.invokePreProcessAfterApproveOrderRefund(order, refund);
        this.orderRepository.save(order);
        this.invokePostProcessAfterApproveOrderRefund(order, refund);
    }

    @Transactional
    @Override
    public void disapproveOrderRefund(String orderId, String refundId, String disapprovalReason) {
        var order = this.requiredOrder(orderId);
        var refund = order.createRefund(refundId).toBuilder().disapprovalReason(disapprovalReason).build();
        refund = this.invokePreProcessBeforeDisapproveOrderRefund(order, refund);
        refund = order.disapproveRefund(refund);
        refund = this.invokePreProcessAfterDisapproveOrderRefund(order, refund);
        this.orderRepository.save(order);
        this.invokePostProcessAfterDisapproveOrderRefund(order, refund);
    }

    @Override
    public OrderRefund reapplyOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.invokePreProcessBeforeReapplyOrderRefund(order, refund);
        refund = order.reapplyRefund(refund);
        refund = this.invokePreProcessAfterReapplyOrderRefund(order, refund);
        this.orderRepository.save(order);
        return this.invokePostProcessAfterReapplyOrderRefund(order, refund);
    }

    @Transactional
    @Override
    public void activeOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.invokePreProcessBeforeActiveOrderRefund(order, refund);
        refund = order.activeRefund(refund);
        this.refundOrderPayment(order, refund.getId(), refund.getAmount());
        refund = this.invokePreProcessAfterActiveOrderRefund(order, refund);
        this.orderRepository.save(order);
        this.invokePostProcessAfterActiveOrderRefund(order, refund);
    }

    @Override
    public Optional<OrderRefund> findOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        return order.findRefund(refundId)
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
    public List<OrderReview> reviewOrder(String orderId, List<OrderReview> newReviews) {
        var order = this.requiredOrder(orderId);
        newReviews.forEach(review -> review.toBuilder().author(this.createNewReviewer(review.getAuthor())).build());
        var reviews = order.review(this.invokePreProcessBeforeReviewOrder(order, newReviews));
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReviewedEvent(savedOrder, reviews));
        return reviews;
    }

    @Override
    public List<OrderRating> getOrderRatings(String orderId) {
        var order = this.requiredOrder(orderId);
        return this.invokePostProcessAfterGetOrderRatings(order, order.getRatings());
    }

    @Override
    public void ratingOrder(String orderId, List<OrderRating> ratings) {
        var order = this.requiredOrder(orderId);
        ratings = this.invokePreProcessBeforeRatingOrder(order, ratings);
        order.rating(ratings);
        this.orderRepository.save(order);
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
    public String invokePreProcessBeforeCloseOrders(List<Order> orders, String reason) {
        return Processors.stream(this.processors)
                .<String>map((processor, identity) -> processor.preProcessBeforeCloseOrders(orders, identity))
                .apply(reason);
    }

    @Override
    public String invokePreProcessBeforeDeclineOrder(Order order, String reason) {
        return Processors.stream(this.processors)
                .<String>map((processor, identity) -> processor.preProcessBeforeDeclineOrder(order, identity))
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
    public OrderRefund invokePreProcessAfterApplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterApplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterApplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterApplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public List<OrderRefund> invokePreProcessBeforeApplyOrderRefunds(Order order, List<OrderRefund> refunds) {
        return Processors.stream(this.processors)
                .<List<OrderRefund>>map((processor, identity) -> processor.preProcessBeforeApplyOrderRefunds(order, identity))
                .apply(refunds);
    }

    @Override
    public List<OrderRefund> invokePreProcessAfterApplyOrderRefunds(Order order, List<OrderRefund> refunds) {
        return Processors.stream(this.processors)
                .<List<OrderRefund>>map((processor, identity) -> processor.preProcessAfterApplyOrderRefunds(order, identity))
                .apply(refunds);
    }

    @Override
    public List<OrderRefund> invokePostProcessAfterApplyOrderRefunds(Order order, List<OrderRefund> refunds) {
        return Processors.stream(this.processors)
                .<List<OrderRefund>>map((processor, identity) -> processor.postProcessAfterApplyOrderRefunds(order, identity))
                .apply(refunds);
    }

    @Override
    public OrderRefund invokePreProcessBeforeCancelOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeCancelOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessAfterCancelOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterCancelOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterCancelOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterCancelOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeApproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeApproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessAfterApproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterApproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterApproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterApproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeDisapproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeDisapproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessAfterDisapproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterDisapproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterDisapproveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterDisapproveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeReapplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeReapplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessAfterReapplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterReapplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterReapplyOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterReapplyOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessBeforeActiveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessBeforeActiveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePreProcessAfterActiveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.preProcessAfterActiveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterActiveOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterActiveOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public OrderRefund invokePostProcessAfterGetOrderRefund(Order order, OrderRefund refund) {
        return Processors.stream(this.processors)
                .<OrderRefund>map((processor, identity) -> processor.postProcessAfterGetOrderRefund(order, identity))
                .apply(refund);
    }

    @Override
    public List<OrderReview> invokePreProcessBeforeReviewOrder(Order order, List<OrderReview> reviews) {
        return Processors.stream(this.processors)
                .<List<OrderReview>>map((processor, identity) -> processor.preProcessBeforeReviewOrder(order, identity))
                .apply(reviews);
    }

    @Override
    public List<OrderRating> invokePostProcessAfterGetOrderRatings(Order order, List<OrderRating> ratings) {
        return Processors.stream(this.processors)
                .<List<OrderRating>>map((processor, identity) -> processor.postProcessAfterGetOrderRatings(order, identity))
                .apply(ratings);
    }

    @Override
    public List<OrderRating> invokePreProcessBeforeRatingOrder(Order order, List<OrderRating> ratings) {
        return Processors.stream(this.processors)
                .<List<OrderRating>>map((processor, identity) -> processor.preProcessBeforeRatingOrder(order, identity))
                .apply(ratings);
    }

    @Override
    public void invokePostProcessAfterCompletion() {
        this.processors.forEach(OrderProcessor::postProcessAfterCompletion);
    }
}
