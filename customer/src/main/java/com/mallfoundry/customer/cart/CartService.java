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
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Optional<Cart> getDefaultCart() {
        String id = "1";
        return this.cartRepository
                .findById(id).or(() -> Optional.of(this.cartRepository.save(new Cart(id))));
    }

    @Transactional
    public Optional<Cart> getCart(String cartId) {
        return this.cartRepository
                .findById(cartId).or(() -> Optional.of(this.cartRepository.save(new Cart(cartId))));
    }

    @Transactional
    public void addCartItem(String cartId, CartItem item) {
        this.getCart(cartId)
                .ifPresent(cart -> cart.getItem(item.getVariantId())
                        .ifPresentOrElse(itemOp -> itemOp.addQuantity(itemOp.getQuantity()), () -> cart.addItem(item)));
    }

    @Transactional
    public void removeCartItem(String cartId, Long variantId) {
        this.getCart(cartId).ifPresent(cart -> cart.getItem(variantId).ifPresent(cart::removeItem));
    }

    @Transactional
    public void removeCartItems(String cartId, List<Long> variantIds) {
        this.getCart(cartId).ifPresent(cart -> cart.removeItems(cart.getItems(variantIds)));
    }

    @Transactional
    public List<CartItem> checkout(String chartId, List<Long> itemIds) {
        return this.getCart(chartId)
                .stream()
                .flatMap(cart -> {
                    List<CartItem> items = cart.getItems(itemIds);
                    cart.removeItems(items);
                    return items.stream();
                }).collect(Collectors.toList());
    }
}
