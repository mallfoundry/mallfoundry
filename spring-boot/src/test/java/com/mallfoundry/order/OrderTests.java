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

@SpringBootTest
public class OrderTests {

    @Autowired
    private InternalOrderService orderService;

    @Test
    @Transactional
    @Rollback(false)
    public void testSubmitOrder() throws CustomerValidException {
        
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAddShipment() throws CustomerValidException {
        Order order = new InternalOrder();
        order.addShipment(new InternalShipment());
    }
}
