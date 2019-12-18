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

package com.mallfoundry.customer.rest;

import com.mallfoundry.customer.cart.CartService;
import com.mallfoundry.customer.cart.Cart;
import com.mallfoundry.customer.cart.CartItem;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CartResourceV1 {

    private final CartService cartService;

    public CartResourceV1(CartService shoppingCartService) {
        this.cartService = shoppingCartService;
    }

    @PostMapping("/carts/{cart}/items")
    public void addCartItem(@PathVariable("cart") String cart,
                            @RequestBody CartItem item) {
        this.cartService.addCartItem(cart, item);
    }

    @DeleteMapping("/carts/{cart}/items/{id}")
    public void removeCartItem(@PathVariable("cart") String cart,
                               @PathVariable("id") Long id) {
        this.cartService.removeCartItem(cart, id);
    }

    @DeleteMapping("/carts/{cart}/items/batch")
    public void removeCartItem(
            @PathVariable("cart") String cart,
            @RequestBody List<Long> variantIds) {
        this.cartService.removeCartItems(cart, variantIds);
    }

    @GetMapping("/carts/{cart}")
    public Cart getCart(@PathVariable("cart") String cart) {
        return this.cartService.getCart(cart);
    }
}
