package com.mallfoundry.buyer.rest;

import com.mallfoundry.buyer.PurchaseOrder;
import com.mallfoundry.buyer.ShoppingCartService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/buyer")
public class BuyerResourceV1 {

    private final ShoppingCartService shoppingCartService;

    public BuyerResourceV1(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/shopping_carts/{cart}/orders")
    public void addOrder(@PathVariable("cart") String cart,
                         @RequestBody PurchaseOrder order) {
        order.setCart(cart);
        this.shoppingCartService.addOrder(order);
    }

    @DeleteMapping("/shopping_carts/{cart}/orders/{order_id}")
    public void removeOrder(@PathVariable("cart") String cart,
                            @PathVariable("order_id") String orderId) {
        PurchaseOrder order = new PurchaseOrder();
        order.setCart(cart);
        order.setId(orderId);
        this.shoppingCartService.removeOrder(order);
    }

    @PatchMapping("/shopping_carts/{cart}/orders")
    public void updateOrder(@PathVariable("cart") String cart,
                            @RequestBody PurchaseOrder order) {
        order.setCart(cart);
        this.shoppingCartService.updateOrder(order);
    }

    @GetMapping("/shopping_carts/{cart}/orders")
    public List<PurchaseOrder> getOrders(@PathVariable("cart") String cart) {
        return this.shoppingCartService.getOrders(cart);
    }
}
