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
import com.mallfoundry.payment.PaymentException;
import com.mallfoundry.payment.PaymentProvider;
import com.mallfoundry.payment.PaymentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class PaymentResourceV1 {

    private final PaymentService paymentService;

    public PaymentResourceV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping("/payments/{payment_provider}/confirm")
    public void confirmPayment(@PathVariable("payment_provider") PaymentProvider provider,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException, PaymentException {
        Map<String, String> parameterMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            parameterMap.put(parameterName, request.getParameter(parameterName));
        }

        PaymentConfirmation confirmation = this.paymentService.confirmPayment(provider, parameterMap);
        response.getWriter().write(confirmation.getBody());
    }
}
