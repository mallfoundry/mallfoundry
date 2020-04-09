package com.mallfoundry.order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Order {

    String getId();

    void setId(String id);

    BillingAddress getBillingAddress();

    void setBillingAddress(BillingAddress billingAddress);

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    List<OrderItem> getItems(List<String> itemIds);

    List<Shipment> getShipments();

    Optional<Shipment> getShipment(String id);

    void addShipment(Shipment shipment);

    Date getCreatedTime();

    Date getPaidTime();

    Date getShippedTime();
}
