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

package com.mallfoundry.rest.payment;

import com.mallfoundry.payment.Payment;
import com.mallfoundry.payment.PaymentService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1")
public class PaymentResourceV1 {

    private final PaymentService paymentService;

    public PaymentResourceV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public EntityModel<Payment> createPayment(@RequestBody PaymentRequest request) {
        var payment = this.paymentService.savePayment(
                this.paymentService.createPayment().toBuilder()
                        .amount(request.getAmount())
                        .reference(request.getReference())
                        .metadata(request.getMetadata())
                        .instrument(this.paymentService.createInstrument(request.getInstrument().getType()))
                        .build());
        var entity = new EntityModel<>(payment, linkTo(methodOn(this.getClass()).getPayment(payment.getId())).withSelfRel());
        this.paymentService
                .getPaymentRedirectUrl(payment.getId())
                .ifPresent(redirectUrl -> entity.add(new Link(redirectUrl, LinkRelation.of("redirect"))));
        return entity;
    }

    @GetMapping("/payments/{id}/validate")
    public void validatePaymentGet(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        var notification = this.paymentService.validatePayment(id, request.getParameterMap());
        if (notification.hasResult()) {
            try (var output = response.getOutputStream()) {
                output.write(notification.getResult());
            }
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
