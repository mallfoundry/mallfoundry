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

package com.mallfoundry.payment.rest;

import com.mallfoundry.payment.PaymentConfirmation;
import com.mallfoundry.payment.PaymentOrder;
import com.mallfoundry.payment.PaymentProvider;
import com.mallfoundry.payment.PaymentService;
import com.mallfoundry.payment.PaymentStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class PaymentResourceV1 {

    private final PaymentService paymentService;

    public PaymentResourceV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment-providers")
    public List<PaymentProvider> getPaymentProviders() {
        return this.paymentService.getPaymentProviders();
    }

    @GetMapping("/payment-orders/{order_id}/status")
    public Optional<PaymentStatus> getOrderStatus(@PathVariable("order_id") Long id) {
        return this.paymentService.getOrder(id).map(PaymentOrder::getStatus);
    }

    @GetMapping("/payment-orders/{order_id}")
    public Optional<PaymentOrder> getOrder(@PathVariable("order_id") Long id) {
        return this.paymentService.getOrder(id);
    }

    @PostMapping("/payment-orders/{order_id}/confirm-payment")
    public void confirmPayment(@PathVariable("order_id") Long orderId,
                               @RequestParam Map<String, String> params,
                               HttpServletResponse response) throws IOException {
        PaymentConfirmation confirmation = this.paymentService.confirmPayment(orderId, params);
        response.getWriter().write(confirmation.getBody());
    }
}
