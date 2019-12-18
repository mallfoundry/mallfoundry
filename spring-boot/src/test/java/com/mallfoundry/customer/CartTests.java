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

package com.mallfoundry.customer;

import com.mallfoundry.customer.cart.Cart;
import com.mallfoundry.customer.cart.CartItem;
import com.mallfoundry.customer.cart.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CartTests {

    @Autowired
    private CartService cartService;

    @Transactional
    @Rollback(false)
    @Test
    public void testAddCartItem() {
        CartItem cartItem = new CartItem("mi", 1000000013L, 1000000037L, 1);
        this.cartService.addCartItem("buyer_1", cartItem);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testUpdateCartId() {
        Cart cart = this.cartService.getCart("cart_1");
        cart.setId("buyer_1");
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testDeleteCartItem() {
        Cart cart = this.cartService.getCart("buyer_1");
        cart.getItem(1000000030L).ifPresent(cart::removeItem);
    }

}
