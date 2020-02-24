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

package com.mallfoundry.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PaymentTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testCapturePayment() throws Exception {
        PaymentOrder order = PaymentOrder.builder()
                .title("title")
                .orders(List.of(1L, 2L))
                .provider(PaymentProviderType.ALIPAY)
                .totalAmount(1.1)
                .build();
        PaymentLink paymentLink = this.paymentService.createOrder(order);
    }
}
