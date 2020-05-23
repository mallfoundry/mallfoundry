package com.mallfoundry.checkout;

import com.mallfoundry.order.Order;
import com.mallfoundry.order.ShippingAddress;

import java.util.List;

public interface Checkout {

    String getId();

    ShippingAddress getShippingAddress();

    void setShippingAddress(ShippingAddress shippingAddress);

    List<CheckoutItem> getItems();

    List<Order> getOrders();

}
