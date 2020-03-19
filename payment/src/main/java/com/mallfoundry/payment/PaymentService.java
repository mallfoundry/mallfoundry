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

import com.mallfoundry.security.SecurityUserHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<PaymentOrder> getOrder(Long id) {
        return this.paymentOrderRepository.findById(id);
    }

    public List<PaymentProvider> getPaymentProviders() {
        return this.clientFactory.getPaymentProviders();
    }

    @Transactional(rollbackFor = PaymentException.class)
    public PaymentLink createOrder(PaymentOrder newOrder) throws PaymentException {
        newOrder.setPayerId(SecurityUserHolder.getUserId());
        newOrder.pending();
        this.paymentOrderRepository.save(newOrder);
        String url = this.clientFactory.getClient(newOrder.getProvider()).createOrder(newOrder);
        return new PaymentLink(newOrder.getId(), url);
    }

    @Transactional
    public PaymentConfirmation confirmPayment(Long orderId, Map<String, String> params) {
        PaymentOrder order = this.paymentOrderRepository.findById(orderId)
                .orElseThrow(() -> new PaymentException(String.format("The payment order(%s) does not exist.", orderId)));
        PaymentConfirmation confirmation = this.clientFactory.getClient(order.getProvider()).confirmPayment(params);
        if (Objects.nonNull(confirmation.getOrderId())) {
            order.setStatus(confirmation.getStatus());
            order.setTransactionId(confirmation.getTransactionId());
            if (order.isSuccess()) {
                this.eventPublisher.publishEvent(new PaidEvent(order));
            }
        }
        return confirmation;
    }

    public void refund(String orderId) {

    }

    public void cancel(String orderId) {

    }
}
