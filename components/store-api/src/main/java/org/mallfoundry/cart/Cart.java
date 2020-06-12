package org.mallfoundry.cart;

import java.io.Serializable;
import java.util.Collection;
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

    void removeItems(Collection<CartItem> items);

    Optional<CartItem> getItem(String itemId);

    List<CartItem> getItems(Collection<String> itemIds);

    void adjustItemQuantity(String itemId, int quantityDelta) throws CartException;

    void checkItem(String itemId);

    void uncheckItem(String itemId);

    void checkItems(Collection<String> itemIds);

    void uncheckItems(Collection<String> itemIds);

    void checkAllItems();

    void uncheckAllItems();

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
