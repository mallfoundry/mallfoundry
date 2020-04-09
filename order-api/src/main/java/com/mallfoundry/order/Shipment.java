package com.mallfoundry.order;

import java.util.Date;
import java.util.List;

public interface Shipment {

    String getId();

    void setId(String id);

    String getOrderId();

    List<? extends OrderItem> getItems();

    boolean containsItem(OrderItem item);

    BillingAddress getBillingAddress();

    void setBillingAddress(BillingAddress billingAddress);

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    String getShippingProvider();

    void setShippingProvider(String shippingProvider);

    String getShippingMethod();

    void setShippingMethod(String shippingMethod);

    String getTrackingNumber();

    void setTrackingNumber(String trackingNumber);

//    void setTrackingCarrier(String trackingCarrier);
//
//    String getTrackingCarrier();

    Date getShippedTime();
}
