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
import org.mallfoundry.data.SliceList;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentService;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.shipping.CarrierService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

public class DefaultOrderService implements OrderService {

    private final OrderConfiguration orderConfiguration;

    private final OrderProcessorsInvoker processorsInvoker;

    private final OrderRepository orderRepository;

    private final OrderSplitter orderSplitter;

    private final CarrierService carrierService;

    private final PaymentService paymentService;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultOrderService(OrderConfiguration orderConfiguration,
                               OrderProcessorsInvoker processorsInvoker,
                               OrderRepository orderRepository,
                               OrderSplitter orderSplitter,
                               CarrierService carrierService,
                               PaymentService paymentService,
                               ApplicationEventPublisher eventPublisher) {
        this.orderConfiguration = orderConfiguration;
        this.processorsInvoker = processorsInvoker;
        this.orderRepository = orderRepository;
        this.orderSplitter = orderSplitter;
        this.carrierService = carrierService;
        this.paymentService = paymentService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new DefaultOrderQuery();
    }

    @Override
    public Order createOrder(String id) {
        return this.orderRepository.create(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
    }

    @Override
    public OrderPayment createOrderPayment() {
        return new DelegatingOrderPayment(this.paymentService.createPayment(null));
    }

    @Override
    public List<Order> splitOrders(List<Order> orders) {
        return this.orderSplitter.splitting(orders);
    }

    private <T, R> Function<List<T>, List<T>> publishOrdersEvent(Function<List<T>, R> function) {
        return ts -> {
            this.eventPublisher.publishEvent(function.apply(ts));
            return ts;
        };
    }

    private <T, R> Function<T, T> publishOrderEvent(Function<T, R> function) {
        return t -> {
            this.eventPublisher.publishEvent(function.apply(t));
            return t;
        };
    }

    @Transactional
    @Override
    public List<Order> placeOrder(Order order) {
        return this.placeOrders(List.of(order));
    }

    private List<Order> invokePlaceOrders(List<Order> orders) {
        orders.forEach(order -> {
            // 设置订单对象的扣减库存方式。
            // 当订单对象下单后或支付后，对订单所持有的库存判断其如何扣减或者归还。
            order.setInventoryDeduction(this.orderConfiguration.getInventoryDeduction());
            order.place(this.orderConfiguration.getPlacingExpires());
        });
        return orders;
    }

    @Transactional
    @Override
    public List<Order> placeOrders(List<Order> orders) {
        return Function.<List<Order>>identity()
                .compose(this.publishOrdersEvent(ImmutableOrdersPlacedEvent::new))
                .compose(this.processorsInvoker::invokePostProcessPlaceOrders)
                .compose(this.orderRepository::saveAll)
                .compose(this::invokePlaceOrders)
                .compose(this.processorsInvoker::invokePreProcessPlaceOrders)
                .<List<Order>>compose(this.orderSplitter::splitting)
                .apply(orders);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return this.orderRepository.findById(orderId)
                .map(this.processorsInvoker::invokePostProcessGetOrder);
    }

    @Override
    public SliceList<Order> getOrders(OrderQuery query) {
        try {
            return Function.<SliceList<Order>>identity()
                    .compose(this.processorsInvoker::invokePostProcessGetOrders)
                    .compose(this.orderRepository::findAll)
                    .compose(this.processorsInvoker::invokePreProcessGetOrders)
                    .apply(query);
        } finally {
            this.processorsInvoker.invokePostProcessAfterCompletion();
        }
    }

    @Override
    public long getOrderCount(OrderQuery query) {
        return this.orderRepository.count(query);
    }

    private Order requiredOrder(String orderId) {
        return this.orderRepository.findById(orderId).orElseThrow(OrderExceptions::notFound);
    }

    private Order copyOrder(final Order source, final Order target) {
        if (isNotBlank(source.getStaffNotes())) {
            target.setStaffNotes(trim(source.getStaffNotes()));
        }
        if (nonNull(source.getStaffStars())) {
            target.setStaffStars(source.getStaffStars());
        }
        return target;
    }

    @Transactional
    @Override
    public Order updateOrder(final Order source) {
        return Function.<Order>identity()
                .compose(this.publishOrderEvent(ImmutableOrderChangedEvent::new))
                .compose(this.orderRepository::save)
                .<Order>compose(target -> copyOrder(source, target))
                .compose(this.processorsInvoker::invokePreProcessUpdateOrder)
                .compose(this::requiredOrder)
                .apply(source.getId());
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
        var order = this.processorsInvoker.invokePreProcessFulfilOrder(requiredOrder(orderId));
        order.fulfil();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderFulfilledEvent(savedOrder));
    }

    @Transactional
    @Override
    public void signOrder(String orderId, String message) {
        var order = this.requiredOrder(orderId);
        message = this.processorsInvoker.invokePreProcessSignOrder(order, message);
        order.sign(message);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderSignedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void receiptOrder(String orderId) {
        var order = this.processorsInvoker.invokePreProcessReceiptOrder(this.requiredOrder(orderId));
        order.receipt();
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderReceivedEvent(savedOrder));
    }

    @Transactional
    @Override
    public void cancelOrder(String orderId, String reason) {
        var order = requiredOrder(orderId);
        reason = this.processorsInvoker.invokePreProcessCancelOrder(order, reason);
        order.cancel(reason);
        var savedOrder = this.orderRepository.save(order);
        this.eventPublisher.publishEvent(new ImmutableOrderCancelledEvent(savedOrder));
    }

    @Transactional
    @Override
    public void cancelOrders(Set<String> orderIds, String reason) {
        var orders = this.orderRepository.findAllById(orderIds);
        if (CollectionUtils.isNotEmpty(orders)) {
            orders.forEach(order -> order.cancel(this.processorsInvoker.invokePreProcessCancelOrder(order, reason)));
            var savedOrders = this.orderRepository.saveAll(orders);
            this.eventPublisher.publishEvent(new ImmutableOrdersCancelledEvent(savedOrders));
        }
    }

    @Transactional
    @Override
    public OrderShipment addOrderShipment(String orderId, OrderShipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.processorsInvoker.invokePreProcessAddOrderShipment(order, shipment);
        shipment.setConsignorId(SubjectHolder.getUserId());
        if (StringUtils.isBlank(shipment.getConsignor())) {
            shipment.setConsignor(SubjectHolder.getNickname());
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
        return Function.<Optional<OrderShipment>>identity()
                .<Order>compose(order -> order.getShipment(shipmentId))
                .compose(this.processorsInvoker::invokePreProcessGetOrderShipment)
                .compose(this::requiredOrder)
                .apply(orderId);
    }

    @Override
    public List<OrderShipment> getOrderShipments(String orderId) {
        return Function.<List<OrderShipment>>identity()
                .<Order>compose(Order::getShipments)
                .compose(this.processorsInvoker::invokePreProcessGetOrderShipments)
                .compose(this::requiredOrder)
                .apply(orderId);
    }

    @Transactional
    @Override
    public void updateOrderShipment(String orderId, OrderShipment shipment) {
        var order = this.requiredOrder(orderId);
        shipment = this.processorsInvoker.invokePreProcessUpdateOrderShipment(order, shipment);
        order.updateShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void updateOrderShipments(String orderId, List<OrderShipment> shipments) {
        var order = this.requiredOrder(orderId);
        shipments = this.processorsInvoker.invokePreProcessUpdateOrderShipments(order, shipments);
        order.updateShipments(shipments);
        this.orderRepository.save(order);
    }

    private OrderShipment requiredOrderShipment(Order order, String shipmentId) {
        return order.getShipment(shipmentId).orElseThrow();
    }

    @Transactional
    @Override
    public void removeOrderShipment(String orderId, String shipmentId) {
        var order = this.requiredOrder(orderId);
        var shipment = this.processorsInvoker.invokePreProcessRemoveOrderShipment(order, this.requiredOrderShipment(order, shipmentId));
        order.removeShipment(shipment);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeOrderShipments(String orderId, Set<String> shipmentIds) {
        var order = this.requiredOrder(orderId);
        var shipments = this.processorsInvoker.invokePreProcessRemoveOrderShipments(order, order.getShipments(shipmentIds));
        order.removeShipments(shipments);
        this.orderRepository.save(order);
    }

    @Override
    public Payment startOrderPayment(OrderPayment orderPayment) throws OrderException {
        var payment = orderPayment.toPayment().toBuilder()
                .payerId(SubjectHolder.getUserId()).payer(SubjectHolder.getNickname()).build();
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
        refund = this.processorsInvoker.invokePreProcessApplyOrderRefund(order, refund);
        refund = order.applyRefund(refund);
        this.orderRepository.save(order);
        return refund;
    }

    private OrderRefund requiredOrderRefund(Order order, String refundId) {
        return order.getRefund(refundId).orElseThrow(OrderExceptions.Refund::notFound);
    }

    @Transactional
    @Override
    public void cancelOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        var refund = this.requiredOrderRefund(order, refundId);
        this.processorsInvoker.invokePreProcessCancelOrderRefund(order, refund);
        order.cancelRefund(refundId);
        this.orderRepository.save(order);
    }

    private void refundOrderPayment(Order order, String refundId, BigDecimal refundAmount) {
        var payRefund = this.paymentService.refundPayment(order.getPaymentId(),
                this.paymentService.createPayment(order.getPaymentId()).createRefund(refundId)
                        .toBuilder().orderId(order.getId()).amount(refundAmount).build());
        if (payRefund.isSucceeded()) {
            order.succeedRefund(refundId);
        } else if (payRefund.isFailed()) {
            order.failRefund(refundId, payRefund.getFailReason());
        }
    }

    @Transactional
    @Override
    public void approveOrderRefund(String orderId, String refundId) {
        var order = this.requiredOrder(orderId);
        var refund = this.requiredOrderRefund(order, refundId);
        this.processorsInvoker.invokePreProcessApproveOrderRefund(order, refund);
        order.approveRefund(refundId);
        this.refundOrderPayment(order, refund.getId(), refund.getTotalAmount());
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void disapproveOrderRefund(String orderId, String refundId, String disapprovedReason) {
        var order = this.requiredOrder(orderId);
        var refund = this.requiredOrderRefund(order, refundId);
        disapprovedReason = this.processorsInvoker.invokePreProcessDisapproveOrderRefund(order, refund, disapprovedReason);
        order.disapproveRefund(refundId, disapprovedReason);
        this.orderRepository.save(order);
    }

    @Transactional
    @Override
    public void activeOrderRefund(String orderId, OrderRefund refund) {
        var order = this.requiredOrder(orderId);
        refund = this.processorsInvoker.invokePreProcessActiveOrderRefund(order, refund);
        order.activeRefund(refund);
        // 获得退款对象，用以更新退款状态。
        var fetchRefund = this.requiredOrderRefund(order, refund.getId());
        this.refundOrderPayment(order, fetchRefund.getId(), fetchRefund.getTotalAmount());
        this.orderRepository.save(order);
    }

    @Override
    public Optional<OrderRefund> getOrderRefund(String orderId, String refundId) {
        return this.requiredOrder(orderId).getRefund(refundId);
    }
}
