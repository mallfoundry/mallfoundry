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
import org.mallfoundry.order.repository.jpa.JpaShipment;
import org.mallfoundry.payment.PaymentStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mallfoundry.order.OrderStatus.AWAITING_FULFILLMENT;
import static org.mallfoundry.order.OrderStatus.AWAITING_PAYMENT;
import static org.mallfoundry.order.OrderStatus.AWAITING_PICKUP;
import static org.mallfoundry.order.OrderStatus.AWAITING_SHIPMENT;
import static org.mallfoundry.order.OrderStatus.CANCELLED;
import static org.mallfoundry.order.OrderStatus.COMPLETED;
import static org.mallfoundry.order.OrderStatus.INCOMPLETE;
import static org.mallfoundry.order.OrderStatus.PARTIALLY_REFUNDED;
import static org.mallfoundry.order.OrderStatus.PARTIALLY_SHIPPED;
import static org.mallfoundry.order.OrderStatus.PENDING;
import static org.mallfoundry.order.OrderStatus.REFUNDED;
import static org.mallfoundry.order.OrderStatus.SHIPPED;

public abstract class OrderSupport implements MutableOrder {

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
    public Optional<OrderItem> getItem(String itemId) {
        return this.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }

    public Optional<OrderItem> getItem(String productId, String variantId) {
        return this.getItems().stream()
                .filter(item -> item.getProductId().equals(productId) && item.getVariantId().equals(variantId))
                .findFirst();
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

    @Override
    public void addShipment(Shipment newShipment) {
        var shipment = JpaShipment.of(newShipment);

        if (Objects.isNull(shipment.getShippingAddress())) {
            shipment.setShippingAddress(this.getShippingAddress());
        }

        shipment.getItems().forEach(item -> {
            var orderItem = this.getItem(item.getProductId(), item.getVariantId()).orElseThrow();
            if (Objects.isNull(item.getName())) {
                item.setName(orderItem.getName());
            }
            if (Objects.isNull(item.getImageUrl())) {
                item.setImageUrl(orderItem.getImageUrl());
            }
        });

        this.setShippedItems(this.getShippedItems() + shipment.getItems().size());
        if (this.getShippedItems() == this.getTotalItems()) {
            this.setStatus(SHIPPED);
        } else {
            this.setStatus(PARTIALLY_SHIPPED);
        }
        this.setShippedTime(shipment.getShippedTime());
        this.getShipments().add(shipment);
    }

    @Override
    public Optional<Shipment> getShipment(String shipmentId) {
        return this.getShipments()
                .stream()
                .filter(shipment -> Objects.equals(shipment.getId(), shipmentId))
                .findFirst();
    }

    @Override
    public List<Shipment> getShipments(Set<String> shipmentIds) {
        return CollectionUtils.isEmpty(shipmentIds) ? Collections.emptyList()
                : this.getShipments()
                        .stream()
                        .filter(shipment -> shipmentIds.contains(shipment.getId()))
                        .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateShipment(Shipment shipment) {
        BeanUtils.copyProperties(shipment, this.getShipment(shipment.getId()).orElseThrow());
    }

    @Override
    public void updateShipments(List<Shipment> shipments) {
        ListUtils.emptyIfNull(shipments).forEach(this::updateShipment);
    }

    @Override
    public void removeShipment(Shipment shipment) {
        this.getShipments().remove(shipment);
    }

    @Override
    public void removeShipments(List<Shipment> shipments) {
        ListUtils.emptyIfNull(shipments).forEach(this::removeShipment);
    }

    @Override
    public void discounts(Map<String, BigDecimal> amounts) {
        amounts.forEach((itemId, discountAmount) ->
                this.getItem(itemId).orElseThrow().setDiscountAmount(discountAmount));
    }

    @Override
    public void discountShippingCosts(Map<String, BigDecimal> shippingCosts) {
        shippingCosts.forEach((itemId, discountCost) ->
                this.getItem(itemId).orElseThrow().setDiscountShippingCost(discountCost));
    }

    @Override
    public boolean isPlaced() {
        return Objects.nonNull(this.getPlacedTime());
    }

    @Override
    public boolean isPaid() {
        return Objects.nonNull(this.getPaidTime())
                && Objects.nonNull(this.getPaymentStatus())
                && this.getPaymentStatus().isCaptured();
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
    public void pay(PaymentInformation payment) throws OrderException {
        this.setPaymentId(payment.getId());
        this.setPaymentMethod(payment.getMethod());
        this.setPaymentStatus(payment.getStatus());
        if (payment.getStatus() == PaymentStatus.PENDING) {
            this.setStatus(AWAITING_PAYMENT);
        } else if (payment.getStatus() == PaymentStatus.CAPTURED) {
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
    public void cancel(String reason) throws OrderException {
        var status = this.getStatus();
        if (!(Objects.equals(PENDING, status) || Objects.equals(AWAITING_PAYMENT, status))) {
            throw new OrderException("The current order status must be awaiting payment or pending");
        }
        this.setStatus(CANCELLED);
        this.setCancelledTime(new Date());
        this.setCancelReason(reason);
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
    public void addRefund(Refund refund) {
        this.setStatus(PARTIALLY_REFUNDED);
        this.setStatus(REFUNDED);
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
}
