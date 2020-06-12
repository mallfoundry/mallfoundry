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

package org.mallfoundry.rest.payment;

import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class PaymentResourceV1 {

    private final PaymentService paymentService;

    public PaymentResourceV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public Payment createPayment(@RequestBody PaymentRequest request) {
        return this.paymentService.createPayment(
                request.assignPayment(
                        this.paymentService.createPayment((String) null)));
    }

    @GetMapping("/payments/{id}/redirect-url")
    public Optional<String> getPaymentRedirectUrl(@PathVariable("id") String id) {
        return this.paymentService.getPaymentRedirectUrl(id);
    }

    @GetMapping("/payments/{id}/return")
    public void sendPaymentReturn(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.paymentService.validatePayment(id, request.getParameterMap());
        var payment = this.paymentService.getPayment(id).orElseThrow();
        if (Objects.nonNull(payment.getReturnUrl())) {
            var returnUrl = UriComponentsBuilder
                    .fromHttpUrl(payment.getReturnUrl())
                    .build(Map.of("payment_id", payment.getId())).toString();
            response.sendRedirect(returnUrl);
        }
    }

    @PostMapping("/payments/{id}/validate")
    public void validatePayment(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        var notification = this.paymentService.validatePayment(id, request.getParameterMap());
        if (notification.hasResult()) {
            try (var output = response.getOutputStream()) {
                output.write(notification.getResult());
            }
        }
    }

    @PatchMapping("/payments/{id}")
    public Payment savePayment(@PathVariable("id") String id, @RequestBody PaymentPatchRequest request) {
        return this.paymentService.getPayment(id).orElseThrow();
    }

    @GetMapping("/payments/{id}")
    public Payment getPayment(@PathVariable("id") String id) {
        return this.paymentService.getPayment(id).orElseThrow();
    }

}
