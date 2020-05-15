package com.mallfoundry.cart;

import java.io.Serializable;
import java.util.Optional;

public interface Cart extends Serializable {

    String getId();

    Optional<CartItem> getItem(String id);

    void addItem(CartItem item);

    void removeItem(CartItem item);

}
