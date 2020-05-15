package com.mallfoundry.store.cart.rest;

import com.mallfoundry.cart.Cart;
import com.mallfoundry.cart.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CartResourceV1 {

    private final CartService cartService;

    public CartResourceV1(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/{token}")
    public Optional<Cart> getCart(@PathVariable("token") String token) {
        return this.cartService.getCart(token);
    }
}
