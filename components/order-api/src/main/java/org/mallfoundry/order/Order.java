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

    String getStaffNotes();

    void setStaffNotes(String staffNotes);

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

//    BigDecimal getTotalOriginalAmount();

    BigDecimal getSubtotalAmount();

    BigDecimal getTotalAmount();

    PaymentDetails getPaymentDetails();

    String getCancelReason();

    Date getCreatedTime();

    Date getPaidTime();

    Date getShippedTime();

    Date getCancelledTime();

    void discounts(Map<String, BigDecimal> amounts);

    void discountShippingCosts(Map<String, BigDecimal> shippingCosts);

    void place();

    void pay(PaymentDetails details);

    void cancel(String reason);

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