/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.cart;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Cart extends Serializable, ObjectBuilder.ToBuilder<Cart.Builder> {

    String getId();

    String getCustomerId();

    void setCustomerId(String customerId);

    CartItemAdjustment createItemAdjustment(String itemId);

    CartItem createItem(String id);

    void addItem(CartItem item);

    void addItems(Collection<CartItem> items);

    Optional<CartItem> getItem(String itemId);

    Optional<CartItem> getItem(String productId, String variantId);

    List<CartItem> getItems(Collection<String> itemIds);

    List<CartItem> getItems();

    void updateItem(CartItem item);

    void adjustItem(CartItemAdjustment adjustment) throws CartException;

    void checkItem(String itemId);

    void uncheckItem(String itemId);

    void checkItems(Collection<String> itemIds);

    void uncheckItems(Collection<String> itemIds);

    void checkAllItems();

    void uncheckAllItems();

    void removeItem(CartItem item);

    void removeItems(Collection<CartItem> items);

    @Override
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
