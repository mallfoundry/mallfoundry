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

package com.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mallfoundry.order.OrderStatus.AWAITING_FULFILLMENT;
import static com.mallfoundry.order.OrderStatus.AWAITING_PAYMENT;
import static com.mallfoundry.order.OrderStatus.AWAITING_PICKUP;
import static com.mallfoundry.order.OrderStatus.AWAITING_SHIPMENT;
import static com.mallfoundry.order.OrderStatus.CANCELLED;
import static com.mallfoundry.order.OrderStatus.COMPLETED;
import static com.mallfoundry.order.OrderStatus.DECLINED;
import static com.mallfoundry.order.OrderStatus.DISPUTED;
import static com.mallfoundry.order.OrderStatus.INCOMPLETE;
import static com.mallfoundry.order.OrderStatus.MANUAL_VERIFICATION_REQUIRED;
import static com.mallfoundry.order.OrderStatus.PARTIALLY_REFUNDED;
import static com.mallfoundry.order.OrderStatus.PARTIALLY_SHIPPED;
import static com.mallfoundry.order.OrderStatus.PENDING;
import static com.mallfoundry.order.OrderStatus.REFUNDED;
import static com.mallfoundry.order.OrderStatus.SHIPPED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_info")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "id_")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "status_")
    private OrderStatus status;

    @Column(name = "customer_id_")
    private String customerId;

    @Column(name = "trade_id_")
    private String tradeId;

    @JsonProperty("shipping_address")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipping_address_id_")
    private ShippingAddress shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id_")
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id_")
    private List<OrderShipment> shipments = new ArrayList<>();

    public Order(String customerId, ShippingAddress shippingAddress) {
        this.setStatus(INCOMPLETE);
        this.setCustomerId(customerId);
        this.setShippingAddress(shippingAddress);
    }

    public void addItem(OrderItem item) {
        this.getItems().add(item);
    }

    public void removeItem(OrderItem item) {
        this.getItems().remove(item);
    }

    public void addShipment(OrderShipment shipment) {
        this.getShipments().add(shipment);
    }

    public void removeShipment(OrderShipment shipment) {
        this.getShipments().remove(shipment);
    }

    public void pending() throws OrderException {
        if (!Objects.equals(INCOMPLETE, this.status)) {
            throw new OrderException("The current state of the order is not incomplete");
        }
        this.setStatus(OrderStatus.PENDING);
    }

    public void awaitingPayment() throws OrderException {
        if (!Objects.equals(PENDING, this.status)) {
            throw new OrderException("The current state of the order is not pending");
        }
        this.setStatus(AWAITING_PAYMENT);
    }

    public void awaitingFulfillment(String tradeId) throws OrderException {
        if (!Objects.equals(AWAITING_PAYMENT, this.status)) {
            throw new OrderException("The current state of the order is not awaiting_payment");
        }
        this.setTradeId(tradeId);
        this.setStatus(AWAITING_FULFILLMENT);
    }

    public void awaitingShipment() throws OrderException {
        if (!Objects.equals(AWAITING_FULFILLMENT, this.status)) {
            throw new OrderException("The current state of the order is not awaiting_fulfillment");
        }
        this.setStatus(AWAITING_SHIPMENT);
    }

    public void partiallyShipped(List<OrderShipment> shipments) {
        this.setStatus(PARTIALLY_SHIPPED);
        this.getShipments().addAll(shipments);
    }

    public void shipped(List<OrderShipment> shipments) {
        this.setStatus(SHIPPED);
        this.getShipments().addAll(shipments);
    }

    public void awaitingPickup() {
        this.setStatus(AWAITING_PICKUP);
    }

    public void completed() {
        this.setStatus(COMPLETED);
    }

    public void manualVerificationRequired() {
        this.setStatus(MANUAL_VERIFICATION_REQUIRED);
    }

    public void disputed() {
        this.setStatus(DISPUTED);
    }

    public void partiallyRefunded() {
        this.setStatus(PARTIALLY_REFUNDED);
    }

    public void refunded() {
        this.setStatus(REFUNDED);
    }

    public void cancelled() {
        this.setStatus(CANCELLED);
    }

    public void declined() {
        this.setStatus(DECLINED);
    }
}
