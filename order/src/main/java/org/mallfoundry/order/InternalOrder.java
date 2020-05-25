/*
 * Copyright 2019 the original author or authors.
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

package org.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mallfoundry.payment.PaymentStatus;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
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
import static org.mallfoundry.order.OrderStatus.CANCELLED;
import static org.mallfoundry.order.OrderStatus.INCOMPLETE;
import static org.mallfoundry.order.OrderStatus.PARTIALLY_SHIPPED;
import static org.mallfoundry.order.OrderStatus.PENDING;
import static org.mallfoundry.order.OrderStatus.SHIPPED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class InternalOrder implements Order {

    @Id
    @Column(name = "id_")
    private String id;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "status_")
    private OrderStatus status = INCOMPLETE;

    @JsonProperty(value = "store_id", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "store_id_")
    private String storeId;

    @JsonProperty("customer_id")
    @Column(name = "customer_id_")
    private String customerId;

    @JsonProperty("customer_message")
    @Column(name = "customer_message_")
    private String customerMessage;

    @JsonProperty("staff_notes")
    @Column(name = "staff_notes_")
    private String staffNotes;

    @JsonProperty("shipping_address")
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

    @JsonProperty(value = "shipped_items", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "shipped_items_")
    private int shippedItems;

    @JsonProperty("payment_details")
    @Embedded
    private InternalPaymentDetails paymentDetails;

    @JsonProperty("cancel_reason")
    private String cancelReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "created_time", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_time_")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "paid_time", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "paid_time_")
    private Date paidTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "shipped_time", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "shipped_time_")
    private Date shippedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "cancelled_time", access = JsonProperty.Access.READ_ONLY)
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
        var shipment = new InternalShipment(shipmentId);
        shipment.setOrderId(this.getId());
        shipment.setShippingAddress(this.getShippingAddress());
        return shipment;
    }

    @Override
    public void addShipment(Shipment shipment) {
        shipment = InternalShipment.of(shipment);

        if (Objects.isNull(shipment.getShippingAddress())) {
            shipment.setShippingAddress(this.shippingAddress);
        }

        this.getShipments().add(shipment);
        this.setShippedItems(this.getShippedItems() + shipment.getItems().size());
        if (this.getShippedItems() == this.getTotalItems()) {
            this.setStatus(SHIPPED);
        } else {
            this.setStatus(PARTIALLY_SHIPPED);
        }
        this.setShippedTime(shipment.getShippedTime());
    }

    @Override
    public void addRefund(Refund refund) {

    }

    @JsonProperty("total_discount_amount")
    @Transient
    @Override
    public BigDecimal getTotalDiscountAmount() {
        return BigDecimal.ZERO.subtract(this.getItems().stream()
                .map(OrderItem::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @JsonProperty("total_shipping_cost")
    @Transient
    @Override
    public BigDecimal getTotalShippingCost() {
        return this.getItems().stream()
                .map(OrderItem::getShippingCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @JsonProperty("subtotal_amount")
    @Transient
    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getItems().stream()
                .map(OrderItem::getSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @JsonProperty("total_amount")
    @Transient
    @Override
    public BigDecimal getTotalAmount() {
        return this.getItems().stream()
                .map(OrderItem::getActualAmount)
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

    @JsonIgnore
    public boolean isAwaitingPayment() {
        return this.getStatus() == PENDING || this.getStatus() == AWAITING_PAYMENT;
    }

    @Override
    public void place() throws OrderException {
        if (this.status != INCOMPLETE) {
            throw new OrderException("The current state of the order is not incomplete");
        }
        this.setStatus(OrderStatus.PENDING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void pay(PaymentDetails details) {
        this.setPaymentDetails(InternalPaymentDetails.of(details));
        if (details.getStatus() == PaymentStatus.CAPTURED) {
            this.setStatus(AWAITING_FULFILLMENT);
            this.setPaidTime(new Date());
        }
    }

    @Override
    public void cancel(String reason) {
        this.setStatus(CANCELLED);
        this.setCancelledTime(new Date());
        this.setCancelReason(reason);
    }
}
