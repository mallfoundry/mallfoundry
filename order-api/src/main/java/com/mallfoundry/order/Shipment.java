package com.mallfoundry.order;

import java.util.Date;
import java.util.List;

public interface Shipment {

    String getId();

    void setId(String id);

    String getShippingProvider();

    void setShippingProvider(String shippingProvider);

    String getShippingMethod();

    void setShippingMethod(String shippingMethod);

    String getTrackingNumber();

    void setTrackingNumber(String trackingNumber);

    String getOrderId();

    List<? extends OrderItem> getItems();

    Date getShippedTime();
}
