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

package com.mallfoundry.customer.application;

import com.mallfoundry.customer.domain.cart.CartItem;
import com.mallfoundry.customer.domain.cart.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository purchaseOrderRepository) {
        this.cartItemRepository = purchaseOrderRepository;
    }

    @Transactional
    public void addItem(CartItem order) {
        this.cartItemRepository.add(order);
    }

    public void removeItem(CartItem order) {
        this.cartItemRepository.delete(order.getId());
    }

    public void updateItem(CartItem order) {
        this.cartItemRepository.update(order);
    }

    public List<CartItem> getItems(String cart) {
        return this.cartItemRepository.findListByCart(cart);
    }
}
