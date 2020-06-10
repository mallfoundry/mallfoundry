package org.mallfoundry.checkout;

import org.mallfoundry.order.Order;
import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Checkout {

    String getId();

    String getCartId();

    void setCartId(String cartId);

    Address getShippingAddress();

    void setShippingAddress(Address shippingAddress);

    CheckoutItem createItem();

    void addItem(CheckoutItem item);

    List<CheckoutItem> getItems();

    List<Order> getOrders();

    BigDecimal getSubtotalAmount();

    Date getCreatedTime();

    void create();
}
