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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.payment.PaymentMethod;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order")
public class InternalOrder implements Order {

    @Id
    @Column(name = "id_")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private OrderStatus status = INCOMPLETE;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "store_name_")
    private String storeName;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "customer_message_")
    private String customerMessage;

    @Column(name = "staff_notes_")
    private String staffNotes;

    @Column(name = "staff_stars_")
    private Integer staffStars;

    @Convert(converter = AddressConverter.class)
    @Column(name = "shipping_address_")
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            targetEntity = InternalOrderItem.class)
    @JoinColumn(name = "order_id_")
    @OrderBy("id ASC")
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            targetEntity = InternalShipment.class)
    @JoinColumn(name = "order_id_")
    private List<Shipment> shipments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            targetEntity = InternalRefund.class)
    @JoinColumn(name = "order_id_")
    private List<Refund> refunds = new ArrayList<>();

    @Column(name = "shipped_items_")
    private int shippedItems;

    @Column(name = "payment_id_")
    private String paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status_")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_")
    private PaymentMethod paymentMethod;

    // Default is 60 * 1000 ms
    @Column(name = "payment_expires_")
    private int paymentExpires = 60 * 1000;

    @Column(name = "cancel_reason_")
    private String cancelReason;

    @Column(name = "placed_time_")
    private Date placedTime;

    @Column(name = "paid_time_")
    private Date paidTime;

    @Column(name = "fulfilled_time_")
    private Date fulfilledTime;

    @Column(name = "shipped_time_")
    private Date shippedTime;

    @Column(name = "signed_time_")
    private Date signedTime;

    @Column(name = "received_time_")
    private Date receivedTime;

    @Column(name = "cancelled_time_")
    private Date cancelledTime;

    public InternalOrder(String id) {
        this.setId(id);
    }

    public static InternalOrder of(Order order) {
        if (order instanceof InternalOrder) {
            return (InternalOrder) order;
        }

        var target = new InternalOrder();
        BeanUtils.copyProperties(order, target);
        return target;
    }

    @Override
    public OrderItem createItem(String itemId) {
        return new InternalOrderItem(itemId);
    }

    @Override
    public void addItem(OrderItem item) {
        this.items.add(item);
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

    public List<OrderItem> getItems(List<String> itemIds) {
        return this.items.stream()
                .filter(item -> itemIds.contains(item.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalItems() {
        return CollectionUtils.size(this.getItems());
    }

    @Override
    public Optional<Shipment> getShipment(String shipmentId) {
        return this.getShipments()
                .stream()
                .filter(shipment -> Objects.equals(shipment.getId(), id))
                .findFirst();
    }

    @Override
    public void setShipment(Shipment shipment) {
        BeanUtils.copyProperties(shipment, this.getShipment(shipment.getId()).orElseThrow());
    }

    @Override
    public void removeShipment(Shipment shipment) {
        this.getShipments().remove(shipment);
    }

    @Override
    public Shipment createShipment(String shipmentId) {
        return new InternalShipment(shipmentId)
                .toBuilder().shippingAddress(this.getShippingAddress()).build();
    }

    @Override
    public void addShipment(Shipment newShipment) {
        var shipment = InternalShipment.of(newShipment);

        if (Objects.isNull(shipment.getShippingAddress())) {
            shipment.setShippingAddress(this.shippingAddress);
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
    public void addRefund(Refund refund) {
        this.setStatus(PARTIALLY_REFUNDED);
        this.setStatus(REFUNDED);
    }

    @Transient
    @Override
    public BigDecimal getTotalDiscountAmount() {
        return this.getItems().stream()
                .map(OrderItem::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
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

    @Transient
    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getItems().stream()
                .map(OrderItem::getSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public List<Shipment> getShipments() {
        return this.shipments;
    }

    @Override
    public void place() throws OrderException {
        if (this.status != INCOMPLETE) {
            throw new OrderException("The current state of the order is not incomplete");
        }
        this.setStatus(OrderStatus.PENDING);
        this.setPlacedTime(new Date());
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
        if (!Objects.equals(AWAITING_PAYMENT, status)
                || !Objects.equals(PENDING, status)) {
            throw new OrderException("The current order status must be awaiting payment or pending");
        }
        this.setStatus(CANCELLED);
        this.setCancelledTime(new Date());
        this.setCancelReason(reason);
    }

    @Override
    public void sign() throws OrderException {
        this.setStatus(AWAITING_PICKUP);
        this.setSignedTime(new Date());
    }

    @Override
    public void receipt() throws OrderException {
        this.setReceivedTime(new Date());
        this.setStatus(COMPLETED);
    }
}
