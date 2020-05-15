package com.mallfoundry.cart;

import java.util.Optional;

public interface CartService {

    Cart createCart();

    CartItem createCartItem(String productId, String variantId, int quantity);

    CartScope getCartScope();

    Optional<Cart> getCart(String token);

    void addCartItem(String token, CartItem item);
}
