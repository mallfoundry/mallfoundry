package com.mallfoundry.cart;

import java.util.Optional;

public interface CartService {

    Cart createCart(String id);

    CartItem createCartItem();

    Cart saveCart(Cart cart);

    void deleteCart(String id);

    void addCartItem(String id, CartItem item);

    void saveCartItem(String id, CartItem item);

    void removeCartItem(String id, String itemId);

    Optional<Cart> getCart(String id);

}
