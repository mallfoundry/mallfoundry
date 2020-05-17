package com.mallfoundry.cart;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Cart extends Serializable {

    String getId();

    String getCustomerId();

    void setCustomerId(String customerId);

    List<CartItem> getItems();

    void addItem(CartItem item);

    void removeItem(CartItem item);

    Optional<CartItem> getItem(String id);

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
