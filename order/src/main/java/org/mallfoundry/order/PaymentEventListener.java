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

package org.mallfoundry.order;

import org.mallfoundry.payment.CapturedEvent;
import org.mallfoundry.payment.Payment;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

@Configuration
public class PaymentEventListener {

    private final OrderService orderService;

    public PaymentEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    private PaymentDetails createPaymentDetails(Payment payment) {
        var instrument = payment.getInstrument();
        return new InternalPaymentDetails(payment.getId(), instrument.getType(), payment.getStatus());
    }

    @EventListener
    public void handleCaptured(CapturedEvent event) {
        var payment = event.getPayment();
        StringUtils.commaDelimitedListToSet(payment.getReference())
                .forEach(orderId -> this.orderService.payOrder(orderId, createPaymentDetails(payment)));
    }
}
