package org.mallfoundry.cart;

import java.util.Collection;
import java.util.Optional;

public interface CartService {

    Cart createCart(String id);

    Cart createCart(Cart cart);

    void deleteCart(String id);

    void addCartItem(String id, CartItem item);

    void updateCartItem(String id, CartItem item);

    void removeCartItem(String id, String itemId);

    void removeCartItems(String id, Collection<String> itemIds);

    void adjustCartItemQuantity(String id, String itemId, int quantityDelta);

    void checkCartItem(String id, String itemId);

    void uncheckCartItem(String id, String itemId);

    void checkCartItems(String id, Collection<String> itemIds);

    void uncheckCartItems(String id, Collection<String> itemIds);

    void checkAllCartItems(String id);

    void uncheckAllCartItems(String id);

    Optional<Cart> getCart(String id);

}
