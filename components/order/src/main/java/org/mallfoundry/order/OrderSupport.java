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
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.order.aftersales.OrderRefund;
import org.mallfoundry.order.aftersales.OrderRefundException;
import org.mallfoundry.order.shipping.OrderShipment;
import org.mallfoundry.order.shipping.OrderShipmentItem;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.util.DecimalUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mallfoundry.order.OrderStatus.AWAITING_FULFILLMENT;
import static org.mallfoundry.order.OrderStatus.AWAITING_PAYMENT;
import static org.mallfoundry.order.OrderStatus.AWAITING_PICKUP;
import static org.mallfoundry.order.OrderStatus.AWAITING_REFUND;
import static org.mallfoundry.order.OrderStatus.AWAITING_SHIPMENT;
import static org.mallfoundry.order.OrderStatus.CANCELLED;
import static org.mallfoundry.order.OrderStatus.CLOSED;
import static org.mallfoundry.order.OrderStatus.COMPLETED;
import static org.mallfoundry.order.OrderStatus.DECLINED;
import static org.mallfoundry.order.OrderStatus.INCOMPLETE;
import static org.mallfoundry.order.OrderStatus.PARTIALLY_REFUNDED;
import static org.mallfoundry.order.OrderStatus.PARTIALLY_SHIPPED;
import static org.mallfoundry.order.OrderStatus.PENDING;
import static org.mallfoundry.order.OrderStatus.REFUNDED;
import static org.mallfoundry.order.OrderStatus.SHIPPED;
import static org.mallfoundry.order.OrderStatus.isAwaitingPayment;
import static org.mallfoundry.order.OrderStatus.isCompleted;
import static org.mallfoundry.order.OrderStatus.isIncomplete;
import static org.mallfoundry.order.OrderStatus.isPending;
import static org.mallfoundry.payment.PaymentStatus.isCaptured;

public abstract class OrderSupport implements MutableOrder {

    @Override
    public OrderId toId() {
        return new ImmutableOrderId(this.getTenantId(), this.getStoreId(), this.getId());
    }

    @Override
    public boolean isPlacingExpired() {
        return Objects.nonNull(this.getPlacedTime())
                && System.currentTimeMillis() >= (this.getPlacedTime().getTime() + this.getPlacingExpires());
    }

    @Override
    public void addItem(OrderItem item) {
        this.getItems().add(item);
    }

    @Override
    public OrderItem getItem(String itemId) {
        return this.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(OrderExceptions.Item::notFound);
    }

    @Override
    public List<OrderItem> getItems(List<String> itemIds) {
        return this.getItems().stream()
                .filter(item -> itemIds.contains(item.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalItems() {
        return CollectionUtils.size(this.getItems());
    }

    private void shipItem(OrderShipmentItem shipmentItem) {
        var item = this.getItem(shipmentItem.getItemId());
        item.ship();
        shipmentItem.setName(item.getName());
        shipmentItem.setImageUrl(item.getImageUrl());
        shipmentItem.setProductId(item.getProductId());
        shipmentItem.setVariantId(item.getVariantId());
    }

    @Override
    public void addShipment(OrderShipment shipment) {
        if (Objects.isNull(shipment.getShippingAddress())) {
            shipment.setShippingAddress(this.getShippingAddress());
        }
        shipment.getItems().forEach(this::shipItem);
        shipment.ship(); // 发货
        this.setShippedItems(this.getShippedItems() + shipment.getItems().size());
        this.setStatus(this.getShippedItems() == this.getTotalItems() ? SHIPPED : PARTIALLY_SHIPPED);
        this.setShippedTime(shipment.getShippedTime());
        this.getShipments().add(shipment);
    }

    @Override
    public Optional<OrderShipment> findShipment(String shipmentId) {
        return this.getShipments()
                .stream()
                .filter(shipment -> Objects.equals(shipment.getId(), shipmentId))
                .findFirst();
    }

    @Override
    public OrderShipment getShipment(String shipmentId) {
        return this.findShipment(shipmentId).orElseThrow(OrderExceptions.Shipment::notFound);
    }

    @Override
    public List<OrderShipment> getShipments(Set<String> shipmentIds) {
        return CollectionUtils.isEmpty(shipmentIds) ? Collections.emptyList()
                : this.getShipments()
                        .stream()
                        .filter(shipment -> shipmentIds.contains(shipment.getId()))
                        .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateShipment(OrderShipment newShipment) {
        var shipment = this.getShipment(newShipment.getId());
        if (StringUtils.isNotBlank(newShipment.getShippingMethod())) {
            shipment.setShippingMethod(newShipment.getShippingMethod());
        }
        if (Objects.nonNull(newShipment.getShippingProvider())) {
            shipment.setShippingProvider(newShipment.getShippingProvider());
        }
        if (Objects.nonNull(newShipment.getTrackingCarrier())) {
            shipment.setTrackingCarrier(newShipment.getTrackingCarrier());
        }
        if (StringUtils.isNotBlank(newShipment.getTrackingNumber())) {
            shipment.setTrackingNumber(newShipment.getTrackingNumber());
        }
    }

    @Override
    public void updateShipments(List<OrderShipment> shipments) {
        ListUtils.emptyIfNull(shipments).forEach(this::updateShipment);
    }

    @Override
    public void removeShipment(OrderShipment shipment) {
        var removed = this.getShipments().remove(shipment);
        if (!removed) {
            throw OrderExceptions.Shipment.notFound();
        }
    }

    @Override
    public void removeShipments(List<OrderShipment> shipments) {
        ListUtils.emptyIfNull(shipments).forEach(this::removeShipment);
    }

    @Override
    public Optional<OrderRefund> findRefund(String refundId) {
        return this.getRefunds().stream()
                .filter(refund -> Objects.equals(refund.getId(), refundId))
                .findFirst();
    }

    @Override
    public OrderRefund getRefund(String refundId) {
        return this.findRefund(refundId).orElseThrow(OrderExceptions.Refund::notFound);
    }

    /**
     * 判断当前订单对象是否未付款。
     *
     * @return true 表示未付款，false 表示已付款。
     */
    private boolean unpaid() {
        return isIncomplete(this.getStatus()) || isPending(this.getStatus()) || isAwaitingPayment(this.getStatus());
    }

    private void applyItemRefund(OrderRefund refund) {
        var item = this.getItem(refund.getItemId());
        // Apply refund
        item.applyRefund(refund.getAmount());
        refund.setItemId(item.getId());
        refund.setProductId(item.getProductId());
        refund.setVariantId(item.getVariantId());
        refund.setName(item.getName());
        refund.setImageUrl(item.getImageUrl());
        refund.setItemAmount(item.getTotalAmount());
        // 如果退款数量为 0，则全部退款。
        if (refund.getQuantity() == 0) {
            refund.setQuantity(item.getQuantity());
        }
    }

    @Override
    public OrderRefund applyRefund(OrderRefund refund) throws OrderRefundException {
        if (this.unpaid()) {
            throw OrderExceptions.unpaid();
        }
        refund.setTenantId(this.getTenantId());
        refund.setStoreId(this.getStoreId());
        refund.setStoreName(this.getStoreName());
        refund.setCustomerId(this.getCustomerId());
        this.applyItemRefund(refund);
        refund.apply();
        this.getRefunds().add(refund);
        // 设置订单状态为等待退款。
        this.setDisputeStatus(AWAITING_REFUND);
        this.setRefundedTime(new Date());
        return refund;
    }

    @Override
    public List<OrderRefund> applyRefunds(List<OrderRefund> refunds) throws OrderRefundException {
        return refunds.stream().map(this::applyRefund).collect(Collectors.toUnmodifiableList());
    }

    /**
     * 更新订单退款状态。
     * <p>如果是部分退款则更新为部分已退款状态。
     * <p>如果订单项中正在退款金额不为零，则更新为退款中。
     * <p>如果是第一次申请退款又取消退款，则还原无退款操作。
     * <p>如果是全额退款将关闭订单。
     */
    private void updateRefundStatus() {
        final var zero = BigDecimal.ZERO;
        var totalAmount = this.getTotalAmount();
        var totalRefundingAmount = BigDecimal.ZERO;
        var totalRefundedAmount = BigDecimal.ZERO;
        for (var item : this.getItems()) {
            totalRefundingAmount = totalRefundingAmount.add(item.getRefundingAmount());
            totalRefundedAmount = totalRefundedAmount.add(item.getRefundedAmount());
        }
        var totalRefundAmount = totalRefundingAmount.add(totalRefundedAmount);
        if (DecimalUtils.equals(zero, totalRefundAmount)) {
            this.setDisputeStatus(null);
        } else if (DecimalUtils.lessThan(zero, totalRefundingAmount)) {
            this.setDisputeStatus(AWAITING_REFUND);
        } else if (DecimalUtils.equals(totalAmount, totalRefundAmount)) {
            this.setDisputeStatus(REFUNDED);
            this.close(OrderMessages.fullRefundReason());
        } else if (DecimalUtils.equals(zero, totalRefundingAmount)) {
            this.setDisputeStatus(PARTIALLY_REFUNDED);
        }
        this.setRefundedTime(DecimalUtils.equals(zero, totalRefundAmount) ? null : new Date());
    }

    @Override
    public OrderRefund cancelRefund(OrderRefund args) throws OrderRefundException {
        var refund = this.getRefund(args.getId());
        this.getItem(refund.getItemId()).cancelRefund(refund.getAmount());
        refund.cancel();
        this.updateRefundStatus();
        return refund;
    }

    @Override
    public OrderRefund approveRefund(OrderRefund args) {
        var refund = this.getRefund(args.getId());
        refund.approve();
        return refund;
    }

    @Override
    public OrderRefund disapproveRefund(OrderRefund args) throws OrderRefundException {
        var refund = this.getRefund(args.getId());
        refund.disapprove(args.getDisapprovalReason());
        this.updateRefundStatus();
        return refund;
    }

    @Override
    public OrderRefund reapplyRefund(OrderRefund source) throws OrderRefundException {
        var refund = this.getRefund(source.getId());
        refund.setReason(source.getReason());
        refund.setNotes(source.getNotes());
        refund.setAttachments(source.getAttachments());
        refund.reapply();
        this.updateRefundStatus();
        return refund;
    }

    @Override
    public OrderRefund activeRefund(OrderRefund refund) throws OrderRefundException {
        this.applyRefund(refund);
        this.approveRefund(refund);
        this.updateRefundStatus();
        return this.getRefund(refund.getId());
    }

    @Override
    public void succeedRefund(OrderRefund args) throws OrderRefundException {
        var refund = this.getRefund(args.getId());
        this.getItem(refund.getItemId()).succeedRefund(refund.getAmount());
        refund.succeed();
        this.updateRefundStatus();
    }

    @Override
    public void failRefund(OrderRefund args) throws OrderRefundException {
        var refund = this.getRefund(args.getId());
        this.getItem(refund.getItemId()).failRefund(refund.getAmount());
        refund.fail(args.getFailReason());
        this.updateRefundStatus();
    }

    @Override
    public boolean canReview() {
        return isCompleted(this.getStatus());
    }

    @Override
    public OrderReview addReview(OrderReview review) throws OrderReviewException {
        if (!this.canReview()) {
            throw OrderExceptions.notReview();

        }
        var item = this.getItem(review.getItemId());
        if (item.isReviewed()) {
            throw OrderExceptions.Item.reviewed(review.getItemId());
        }
        item.review();
        review.setOrderId(this.getId());
        review.setProductId(item.getProductId());
        review.setVariantId(item.getVariantId());
        review.setOptionSelections(item.getOptionSelections());
        review.setItemName(item.getName());
        review.review();
        this.getReviews().add(review);
        return review;
    }

    @Override
    public List<OrderReview> addReviews(List<OrderReview> reviews) throws OrderReviewException {
        return reviews.stream().map(this::addReview).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public OrderReview getReview(String reviewId) {
        return this.getReviews().stream()
                .filter(review -> Objects.equals(review.getId(), reviewId))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void discounts(Map<String, BigDecimal> amounts) {
        amounts.forEach((itemId, discountAmount) -> this.getItem(itemId).setDiscountAmount(discountAmount));
    }

    @Override
    public void discountShippingCosts(Map<String, BigDecimal> shippingCosts) {
        shippingCosts.forEach((itemId, discountCost) -> this.getItem(itemId).setDiscountShippingCost(discountCost));
    }

    @Override
    public boolean isPlaced() {
        return Objects.nonNull(this.getPlacedTime());
    }

    @Override
    public boolean canPay() {
        // 已下单、未支付、已下单状态、下单未过期
        return this.isPlaced() && !this.isPaid() && (isPending(this.getStatus()) || isAwaitingPayment(this.getStatus())) && !this.isPlacingExpired();
    }

    @Override
    public boolean isPaid() {
        return Objects.nonNull(this.getPaidTime()) && isCaptured(this.getPaymentStatus());
    }

    @Override
    public void place(int placingExpires) throws OrderException {
        Assert.notNull(this.getSource(), OrderMessages.Source.notNull());
        if (this.getStatus() != INCOMPLETE) {
            throw new OrderException("The current state of the order is not incomplete");
        }
        this.setStatus(OrderStatus.PENDING);
        this.setPlacingExpires(placingExpires);
        this.setPlacedTime(new Date());
        this.setPlacingExpiredTime(new Date(System.currentTimeMillis() + placingExpires));
    }

    @Override
    public void pay(OrderPaymentResult result) throws OrderException {
        this.setPaymentId(result.getId());
        this.setPaymentMethod(result.getMethod());
        this.setPaymentStatus(result.getStatus());
        if (result.isPending()) {
            this.setStatus(AWAITING_PAYMENT);
        } else if (result.isCaptured()) {
            this.setStatus(AWAITING_FULFILLMENT);
            this.setPaidTime(new Date());
        }
    }

    @Override
    public void fulfil() {
        this.setStatus(AWAITING_SHIPMENT);
        this.setFulfilledTime(new Date());
    }

    @Override
    public void cancel(String cancelReason) throws OrderException {
        var status = this.getStatus();
        if (!(Objects.equals(PENDING, status) || Objects.equals(AWAITING_PAYMENT, status))) {
            throw new OrderException("The current order status must be awaiting payment or pending");
        }
        this.setStatus(CANCELLED);
        this.setCancelReason(cancelReason);
        this.setCancelledTime(new Date());
    }

    @Override
    public void close(String closeReason) throws OrderException {
        this.setStatus(CLOSED);
        this.setCloseReason(closeReason);
        this.setClosedTime(new Date());
    }

    @Override
    public void sign(String message) throws OrderException {
        this.setSignMessage(message);
        this.setStatus(AWAITING_PICKUP);
        this.setSignedTime(new Date());
    }

    @Override
    public void receipt() throws OrderException {
        this.setReceivedTime(new Date());
        this.setStatus(COMPLETED);
    }

    @Override
    public void decline(String declineReason) {
        this.setDeclineReason(declineReason);
        this.setDeclinedTime(new Date());
        this.setStatus(DECLINED);
    }

    @Override
    public BigDecimal getTotalDiscountAmount() {
        return this.getItems().stream()
                .map(OrderItem::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalShippingCost() {
        return this.getItems().stream()
                .map(OrderItem::getShippingCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalDiscountShippingCost() {
        return this.getItems().stream()
                .map(OrderItem::getDiscountShippingCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return this.getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalAmount() {
        return this.getItems().stream()
                .map(OrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getItems().stream()
                .map(OrderItem::getSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final OrderSupport order;

        public BuilderSupport(OrderSupport order) {
            this.order = order;
        }

        @Override
        public Builder customerId(String customerId) {
            this.order.setCustomerId(customerId);
            return this;
        }

        @Override
        public Builder shippingAddress(Address shippingAddress) {
            this.order.setShippingAddress(shippingAddress);
            return this;
        }

        @Override
        public Builder pay(OrderPaymentResult payment) {
            this.order.pay(payment);
            return this;
        }

        @Override
        public Builder item(OrderItem item) {
            this.order.addItem(item);
            return this;
        }

        @Override
        public Builder item(Function<OrderItem, OrderItem> function) {
            return this.item(function.apply(this.order.createItem(null)));
        }

        @Override
        public Order build() {
            return this.order;
        }
    }
}
