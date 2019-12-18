/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.customer.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Cart getCart(String cartId) {
        return this.cartRepository
                .findById(cartId).orElseGet(() -> this.cartRepository.save(new Cart(cartId)));
    }

    @Transactional
    public void addCartItem(String cartId, CartItem item) {
        Cart cart = this.getCart(cartId);
        Optional<CartItem> itemOptional = cart.getItem(item.getVariantId());
        if (itemOptional.isPresent()) {
            itemOptional.get().addQuantity(item.getQuantity());
        } else {
            cart.addItem(item);
        }
    }

    @Transactional
    public void removeCartItem(String cartId, String variantId) {
        Cart cart = this.getCart(cartId);
        cart.getItem(variantId).ifPresent(cart::removeItem);
    }

    @Transactional
    public void removeCartItems(String cartId, List<String> variantIds) {
        Cart cart = this.getCart(cartId);
        cart.removeItems(cart.getItems(variantIds));
    }
}
