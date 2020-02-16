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

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class PaymentService {

    private final PaymentClientFactory clientFactory;

    private final PaymentOrderRepository paymentOrderRepository;

    private final ApplicationEventPublisher eventPublisher;

    public PaymentService(PaymentClientFactory clientFactory,
                          PaymentOrderRepository paymentOrderRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.clientFactory = clientFactory;
        this.eventPublisher = eventPublisher;
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Transactional(rollbackFor = PaymentException.class)
    public String capturePayment(PaymentOrder newOrder) throws PaymentException {
        PaymentOrder savedOrder = this.paymentOrderRepository.findById(newOrder.getOrderId()).orElse(null);

        if (Objects.nonNull(savedOrder)) {
            if (!savedOrder.isPending()) {
                throw new PaymentException("The order cannot be captured.");
            }

            if (!Objects.equals(newOrder.getTotalAmount(), savedOrder.getTotalAmount())) {
                throw new PaymentException("The total amount is not consistent.");
            }
        }

        String paymentUrl = this.clientFactory.getClient(newOrder.getProvider()).capturePayment(newOrder);
        if (Objects.isNull(savedOrder)) {
            this.paymentOrderRepository.save(newOrder);
        }
        return paymentUrl;
    }

    @Transactional(rollbackFor = PaymentException.class)
    public PaymentConfirmation confirmPayment(PaymentProvider provider,
                                              Map<String, String> params) throws PaymentException {
        PaymentConfirmation confirmation =
                this.clientFactory.getClient(provider).confirmPayment(params);

        PaymentOrder order = this.paymentOrderRepository.findById(confirmation.getOrderId()).orElseThrow();
        order.success();
        this.eventPublisher.publishEvent(new PaidEvent(order));
        return confirmation;
    }

    public void refund(String orderId) {

    }

    public void cancel(String orderId) {

    }
}
