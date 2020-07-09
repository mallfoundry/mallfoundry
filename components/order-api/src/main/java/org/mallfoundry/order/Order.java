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

import org.mallfoundry.shipping.Address;
import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface Order {

    String getId();

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    String getCustomerId();

    void setCustomerId(String customerId);

    String getStoreId();

    void setStoreId(String storeId);

    String getStoreName();

    void setStoreName(String storeName);

    OrderStatus getStatus();

    String getStaffNotes();

    void setStaffNotes(String staffNotes);

    Integer getStaffStars();

    void setStaffStars(Integer staffStars);

    List<OrderItem> getItems();

    OrderItem createItem(String itemId);

    void addItem(OrderItem item);

    Optional<OrderItem> getItem(String itemId);

    void setItems(List<OrderItem> items);

    List<OrderItem> getItems(List<String> itemIds);

    Shipment createShipment(String shipmentId);

    void addShipment(Shipment shipment);

    List<Shipment> getShipments();

    Optional<Shipment> getShipment(String id);

    void setShipment(Shipment shipment);

    void removeShipment(Shipment shipment);

    List<Refund> getRefunds();

    void addRefund(Refund refund);

    int getTotalItems();

    int getShippedItems();

    BigDecimal getTotalDiscountAmount();

    BigDecimal getTotalShippingCost();

    BigDecimal getTotalDiscountShippingCost();

    BigDecimal getTotalPrice();

    BigDecimal getTotalAmount();

    BigDecimal getSubtotalAmount();

    PaymentDetails getPaymentDetails();

    int getPaymentExpires();

    void setPaymentExpires(int paymentExpires);

    String getCancelReason();

    Date getCreatedTime();

    Date getPlacedTime();

    Date getPaidTime();

    Date getFulfilledTime();

    Date getShippedTime();

    Date getSignedTime();

    Date getReceivedTime();

    Date getCancelledTime();

    void discounts(Map<String, BigDecimal> amounts);

    void discountShippingCosts(Map<String, BigDecimal> shippingCosts);

    void place() throws OrderException;

    void pay(PaymentDetails details) throws OrderException;

    void fulfil() throws OrderException;

    void sign() throws OrderException;

    void receipt() throws OrderException;

    void cancel(String reason) throws OrderException;

    default Builder toBuilder() {
        return new BuilderSupport(this);
    }

    interface Builder extends ObjectBuilder<Order> {

        Builder customerId(String customerId);

        Builder shippingAddress(Address shippingAddress);

        Builder item(OrderItem item);

        Builder item(Function<Order, OrderItem> item);
    }

    class BuilderSupport implements Builder {

        private final Order order;

        public BuilderSupport(Order order) {
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
        public Builder item(OrderItem item) {
            this.order.addItem(item);
            return this;
        }

        @Override
        public Builder item(Function<Order, OrderItem> item) {
            return this.item(item.apply(this.order));
        }

        @Override
        public Order build() {
            return this.order;
        }
    }
}
