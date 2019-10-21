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

package com.mallfoundry.buyer.domain;

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
    private ShoppingCartService shoppingCartService;

    @Test
    public void testAddOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setCart("buyer_1");
        order.setProductId("product_1");
        order.setSpecs(List.of(1, 2, 3));
        order.setQuantity(20);
        shoppingCartService.addOrder(order);
    }

    @Test
    public void testUpdateOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setId("10000000000002");
        order.setCart("buyer_1");
        order.setProductId("product_1");
        order.setSpecs(List.of(2, 4, 5));
        order.setQuantity(30);
        shoppingCartService.updateOrder(order);
    }

    @Test
    public void testRemoveOrder() {
        PurchaseOrder order = new PurchaseOrder();
        order.setId("1");
        shoppingCartService.removeOrder(order);
    }

    @Test
    public void testGetOrders() {
        List<PurchaseOrder> orders = this.shoppingCartService.getOrders("buyer_1");
        System.out.println(orders);
    }
}
