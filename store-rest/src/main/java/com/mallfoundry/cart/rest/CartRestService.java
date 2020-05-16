package com.mallfoundry.cart.rest;

import com.mallfoundry.cart.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartRestService {

    private final CartService cartService;

    public CartRestService(CartService cartService) {
        this.cartService = cartService;
    }

    @Transactional
    public Optional<CartResponse> getCart(String token) {
        return this.cartService.getCart(token).map(CartResponse::new);
    }

    @Transactional
    public CartItemResponse addCartItem(String token, CartItemRequest request) {
        var item = this.cartService.createCartItem().toBuilder()
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .quantity(request.getQuantity())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();
        this.cartService.addCartItem(token, item);
        return new CartItemResponse(item);
    }


    @Transactional
    public void saveCartItem(String token, String itemId, CartItemRequest request) {
        var item = this.cartService.getCart(token).orElseThrow().getItem(itemId).orElseThrow().toBuilder()
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .quantity(request.getQuantity())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();
        this.cartService.saveCartItem(token, item);
    }

    @Transactional
    public void removeCartItem(String token, String itemId) {
        this.cartService.removeCartItem(token, itemId);
    }
}
