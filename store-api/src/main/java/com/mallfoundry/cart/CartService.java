package com.mallfoundry.cart;

import java.util.Optional;

public interface CartService {

    Cart createCart();

    CartItem createCartItem();

    Optional<Cart> getCart(String token);

    void addCartItem(String token, CartItem item);

    void saveCartItem(String token, CartItem item);

    void removeCartItem(String token, String itemId);

}
