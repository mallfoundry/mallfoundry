package com.github.shop.buyer;

import java.util.List;

/**
 * This is a shopping cart service, To manage the buyer's purchase order.
 *
 * @author Zhi Tang
 */
public interface ShoppingCartService {

    void addOrder(PurchaseOrder order);

    void removeOrder(PurchaseOrder order);

    void updateOrder(PurchaseOrder order);

    List<PurchaseOrder> getOrders(String cart);
}
