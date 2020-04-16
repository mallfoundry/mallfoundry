package com.mallfoundry.order;

import java.util.Date;
import java.util.List;

public interface Shipment {

    String getId();

    void setId(String id);

    String getOrderId();

    String getConsignorId();

    String getConsignor();

    List<OrderItem> getItems();

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    String getShippingProvider();

    void setShippingProvider(String shippingProvider);

    String getShippingMethod();

    void setShippingMethod(String shippingMethod);

    String getTrackingNumber();

    void setTrackingNumber(String trackingNumber);

    Date getShippedTime();
}
