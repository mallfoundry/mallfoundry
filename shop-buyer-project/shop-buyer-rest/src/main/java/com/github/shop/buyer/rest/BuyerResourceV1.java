package com.github.shop.buyer.rest;

import com.github.shop.buyer.PurchaseOrder;
import com.github.shop.buyer.ShoppingCartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/buyer")
public class BuyerResourceV1 {

    private final ShoppingCartService shoppingCartService;

    public BuyerResourceV1(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/purchase/orders")
    public void addOrder(@RequestBody PurchaseOrder order) {
        this.shoppingCartService.addOrder(order);
    }
}
