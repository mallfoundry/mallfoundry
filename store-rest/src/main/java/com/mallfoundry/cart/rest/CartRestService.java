package com.mallfoundry.cart.rest;

import com.mallfoundry.cart.CartException;
import com.mallfoundry.cart.CartItem;
import com.mallfoundry.cart.CartService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartRestService {

    private final CartService cartService;

    public CartRestService(CartService cartService) {
        this.cartService = cartService;
    }

    private CartItem createCartItemFromRequest(CartItemRequest request) {
        return this.cartService.createCartItem().toBuilder()
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .quantity(request.getQuantity())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public CartResponse createCart(CreateCartRequest request) {
        if (CollectionUtils.isEmpty(request.getItems())) {
            throw new CartException("The cart items must not be empty");
        }
        var cart = this.cartService.createCart(request.getId());
        request.getItems().stream().map(this::createCartItemFromRequest).forEach(cart::addItem);
        return new CartResponse(this.cartService.saveCart(cart));
    }

    public void deleteCart(String id) {
        this.cartService.deleteCart(id);
    }

    @Transactional
    public CartItemResponse addCartItem(String id, CartItemRequest request) {
        var item = this.cartService.createCartItem().toBuilder()
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .quantity(request.getQuantity())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();
        this.cartService.addCartItem(id, item);
        return new CartItemResponse(item);
    }

    @Transactional
    public void saveCartItem(String id, String itemId, CartItemRequest request) {
        var item = this.cartService.getCart(id).orElseThrow().getItem(itemId).orElseThrow().toBuilder()
                .productId(request.getProductId())
                .variantId(request.getVariantId())
                .quantity(request.getQuantity())
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .build();
        this.cartService.saveCartItem(id, item);
    }

    public void removeCartItem(String id, String itemId) {
        this.cartService.removeCartItem(id, itemId);
    }

    public void removeCartItems(String id, List<String> itemIds) {
        this.cartService.removeCartItems(id, itemIds);
    }

    @Transactional
    public Optional<CartResponse> getCart(String id) {
        return this.cartService.getCart(id).map(CartResponse::new);
    }
}
