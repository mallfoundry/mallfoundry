package com.github.shop.buyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingCartTests {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void testAddOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setCart("buyer_1");
        order.setProductId("product_1");
        order.setSpecs(List.of(1, 2, 3));
        order.setQuantity(20);
        shoppingCartService.addOrder(order);
    }

    @Test
    public void testUpdateOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setId("10000000000002");
        order.setCart("buyer_1");
        order.setProductId("product_1");
        order.setSpecs(List.of(2, 4, 5));
        order.setQuantity(30);
        shoppingCartService.updateOrder(order);
    }

    @Test
    public void testRemoveOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setId("1");
        shoppingCartService.removeOrder(order);
    }

    @Test
    public void testGetOrders() {
        List<PurchaseOrder> orders = this.shoppingCartService.getOrders("buyer_1");
        System.out.println(orders);
    }
}
