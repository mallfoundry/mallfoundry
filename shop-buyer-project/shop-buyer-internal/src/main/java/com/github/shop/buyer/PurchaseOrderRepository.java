package com.github.shop.buyer;

import java.util.List;

/**
 * This is a purchase order repository for storing administrative orders.
 *
 * @author Zhi Tang
 */
public interface PurchaseOrderRepository {

    void save(PurchaseOrder order);

    void update(PurchaseOrder order);

    void delete(String orderId);

    List<PurchaseOrder> findListByCart(String cart);
}
