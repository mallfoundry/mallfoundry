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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CartService {

    Cart createCart(String id);

    Cart createCart(Cart cart);

    CartItem addCartItem(String id, CartItem item);

    List<CartItem> addCartItems(String id, Collection<CartItem> items);

    Optional<Cart> getCart(String id);

    void updateCartItem(String id, CartItem item);

    void adjustCartItem(String id, CartItemAdjustment adjustment);

    void adjustCartItems(String id, List<CartItemAdjustment> adjustments);

    void checkCartItem(String id, String itemId);

    void uncheckCartItem(String id, String itemId);

    void checkCartItems(String id, Collection<String> itemIds);

    void uncheckCartItems(String id, Collection<String> itemIds);

    void checkAllCartItems(String id);

    void uncheckAllCartItems(String id);

    void removeCartItem(String id, String itemId);

    void removeCartItems(String id, Collection<String> itemIds);

    void deleteCart(String id);
}
