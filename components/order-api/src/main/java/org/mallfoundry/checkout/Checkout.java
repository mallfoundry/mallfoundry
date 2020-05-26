package org.mallfoundry.checkout;

import org.mallfoundry.order.Order;
import org.mallfoundry.shipping.Address;

import java.util.List;

public interface Checkout {

    String getCartId();

    void setCartId(String cartId);

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    CheckoutItem createItem();

    void addItem(CheckoutItem item);

    List<CheckoutItem> getItems();

    List<Order> getOrders();

}
