/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderTests {

    @Autowired
    private InternalOrderService orderService;

    @Test
    @Transactional
    @Rollback(false)
    public void testSubmitOrder() throws CustomerValidException {
        List<InternalOrderItem> items = new ArrayList<>();
        items.add(InternalOrderItem.builder()
                .storeId("mi")
//                .storeId("mi")
                .productId(1L)
                .variantId(1L)
                .quantity(1).build());
        ShippingAddress shippingAddress =
                ShippingAddress.builder().consignee("唐治")
                        .postalCode("260000").countryCode("86").mobile("1560000000")
                        .address("山东省济南市").location("064栋").build();
//        Order order = new Order(123L, shippingAddress, items);
//        this.orderService.createOrder(order);
    }
}
