package com.mallfoundry.cart;

import java.util.List;
import java.util.Optional;

public interface CartService {

    Cart createCart(String id);

    CartItem createCartItem();

    Cart saveCart(Cart cart);

    void deleteCart(String id);

    void addCartItem(String id, CartItem item);

    void setCartItem(String id, CartItem item);

    void removeCartItem(String id, String itemId);

    void removeCartItems(String id, List<String> itemIds);

    Optional<Cart> getCart(String id);

}
