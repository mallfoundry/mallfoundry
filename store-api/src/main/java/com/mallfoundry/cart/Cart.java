package com.mallfoundry.cart;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Cart extends Serializable {

    String getId();

    List<CartItem> getItems();

    void addItem(CartItem item);

    void removeItem(CartItem item);

    Optional<CartItem> getItem(String id);
}
