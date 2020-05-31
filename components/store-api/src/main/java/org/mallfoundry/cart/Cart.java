package org.mallfoundry.cart;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Cart extends Serializable {

    String getId();

    String getCustomerId();

    void setCustomerId(String customerId);

    List<CartItem> getItems();

    CartItem createItem(String id);

    void addItem(CartItem item);

    void setItem(CartItem item);

    void removeItem(CartItem item);

    void removeItems(List<CartItem> items);

    Optional<CartItem> getItem(String itemId);

    List<CartItem> getItems(List<String> itemIds);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final Cart cart;

        public Builder(Cart cart) {
            this.cart = cart;
        }

        public Builder customerId(String customerId) {
            this.cart.setCustomerId(customerId);
            return this;
        }

        public Cart build() {
            return this.cart;
        }
    }
}
