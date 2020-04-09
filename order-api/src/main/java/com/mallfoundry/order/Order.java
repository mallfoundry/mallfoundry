package com.mallfoundry.order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Order {

    String getId();

    BillingAddress getBillingAddress();

    void setBillingAddress(BillingAddress billingAddress);

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    String getCustomerId();

    void setCustomerId(String customerId);

    List<OrderItem> getItems();

    void setItems(List<OrderItem> items);

    List<OrderItem> getItems(List<String> itemIds);

    List<Shipment> getShipments();

    Optional<Shipment> getShipment(String id);

    void addShipment(Shipment shipment);

    int getTotalItems();

    int getShippedItems();

    Date getCreatedTime();

    Date getPaidTime();

    Date getShippedTime();
}
