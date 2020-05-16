package com.mallfoundry.cart.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CartResourceV1 {

    private final CartRestService cartRestService;

    public CartResourceV1(CartRestService cartRestService) {
        this.cartRestService = cartRestService;
    }

    @GetMapping("/carts/{token}")
    public Optional<CartResponse> getCart(@PathVariable("token") String token) {
        return this.cartRestService.getCart(token);
    }

    @PostMapping("/carts/{token}/items")
    public CartItemResponse addCartItem(@PathVariable("token") String token, @RequestBody CartItemRequest request) {
        return this.cartRestService.addCartItem(token, request);
    }

    @DeleteMapping("/carts/{token}/items/{item_id}")
    public void removeCartItem(@PathVariable("token") String token, @PathVariable("item_id") String itemId) {
        this.cartRestService.removeCartItem(token, itemId);
    }

    @PatchMapping("/carts/{token}/items/{item_id}")
    public void saveCartItem(@PathVariable("token") String token, @PathVariable("item_id") String itemId, @RequestBody CartItemRequest request) {
        this.cartRestService.saveCartItem(token, itemId, request);
    }
}
