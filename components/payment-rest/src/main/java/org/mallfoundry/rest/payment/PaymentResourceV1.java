/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.rest.payment;

import org.apache.commons.lang3.Functions;
import org.mallfoundry.payment.Payment;
import org.mallfoundry.payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class PaymentResourceV1 {

    private final PaymentService paymentService;

    public PaymentResourceV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments/{id}")
    public Optional<Payment> getPayment(@PathVariable("id") String id) {
        return this.paymentService.getPayment(id);
    }

    @GetMapping("/payments/{id}/redirect-url")
    public Optional<String> getPaymentRedirectUrl(@PathVariable("id") String id) {
        return this.paymentService.getPaymentRedirectUrl(id);
    }

    @GetMapping("/payments/{id}/redirect")
    public void redirectPayment(@PathVariable("id") String id, HttpServletResponse response) {
        this.paymentService.getPaymentRedirectUrl(id).ifPresent(Functions.asConsumer(response::sendRedirect));
    }

    @GetMapping("/payments/{id}/return-url")
    public Optional<String> getPaymentReturnUrl(@PathVariable("id") String id) {
        return this.paymentService.getPaymentReturnUrl(id);
    }

    @GetMapping("/payments/{id}/return")
    public void sendPaymentReturn(@PathVariable("id") String id, HttpServletResponse response) {
        this.paymentService.getPaymentReturnUrl(id).ifPresent(Functions.asConsumer(response::sendRedirect));
    }

    private Map<String, String> createSingleValueParameters(HttpServletRequest request) {
        var map = new LinkedHashMap<String, String>();
        var names = request.getParameterNames();
        while (names.hasMoreElements()) {
            var name = names.nextElement();
            map.put(name, request.getParameter(name));
        }
        return map;
    }

    @PostMapping("/payments/{id}/notify")
    public void notifyPayment(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        var notification = this.paymentService.notifyPayment(id, this.createSingleValueParameters(request));
        if (notification.hasResult()) {
            try (var output = response.getOutputStream()) {
                output.write(notification.getResult());
            }
        }
    }
}
