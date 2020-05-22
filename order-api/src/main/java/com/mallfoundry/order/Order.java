package com.mallfoundry.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Order {

    String getId();

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    String getCustomerId();

    void setCustomerId(String customerId);

    String getStoreId();

    void setStoreId(String storeId);

    String getStaffNotes();

    void setStaffNotes(String staffNotes);

    List<OrderItem> getItems();

    void setItems(List<OrderItem> items);

    Optional<OrderItem> getItem(String itemId);

    List<OrderItem> getItems(List<String> itemIds);

    List<Shipment> getShipments();

    Optional<Shipment> getShipment(String id);

    void addShipment(Shipment shipment);

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

    void pending();

    void pay(PaymentDetails details);

    void cancel(String reason);
}
