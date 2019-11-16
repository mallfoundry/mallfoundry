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

import com.mallfoundry.customer.application.CartService;
import com.mallfoundry.customer.domain.cart.CartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingCartTests {

    @Autowired
    private CartService cartService;

    @Test
    public void testAddItem() {
        CartItem item = new CartItem();
        item.setCart("buyer_1");
        item.setProductId("product_1");
        item.setOptions(List.of(1, 2, 3));
        item.setQuantity(20);
        cartService.addItem(item);
    }

    @Test
    public void testUpdateItem() {
        CartItem item = new CartItem();
        item.setId("10000000000002");
        item.setCart("buyer_1");
        item.setProductId("product_1");
        item.setOptions(List.of(2, 4, 5));
        item.setQuantity(30);
        cartService.updateItem(item);
    }

    @Test
    public void testRemoveItem() {
        CartItem item = new CartItem();
        item.setId("1");
        cartService.removeItem(item);
    }

    @Test
    public void testGetItems() {
        List<CartItem> items = this.cartService.getItems("buyer_1");
        System.out.println(items);
    }
}
