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

package org.mallfoundry.payment;

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InternalPaymentService implements PaymentService {

    private static final String PAYMENT_ID_VALUE_NAME = "payment.id";

    private final PaymentRepository paymentRepository;

    private final PaymentClientFactory paymentClientFactory;

    private final ApplicationEventPublisher eventPublisher;

    public InternalPaymentService(PaymentRepository paymentRepository,
                                  PaymentClientFactory paymentClientFactory,
                                  ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentClientFactory = paymentClientFactory;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Payment createPayment(String id) {
        var payment = new InternalPayment(id);
        payment.pending();
        this.eventPublisher.publishEvent(new InternalPaymentPendingEvent(payment));
        return payment;
    }

    @Transactional
    @Override
    public Payment createPayment(Payment aPayment) {
        var payment = InternalPayment.of(aPayment);
        payment.setId(PrimaryKeyHolder.next(PAYMENT_ID_VALUE_NAME));
        payment.pending();
        return this.paymentRepository.save(payment);
    }

    @Override
    public void capturePayment(String id) throws PaymentException {

    }

    @Transactional
    @Override
    public PaymentNotification validatePayment(String id, Object parameters) throws PaymentException {
        var payment = this.paymentRepository.findById(id).orElseThrow();
        var paymentClient = this.paymentClientFactory.getClient(payment).orElseThrow();
        var notification = paymentClient.createPaymentNotification(parameters);
        paymentClient.validateNotification(notification);
        if (notification.isCaptured()) {
            payment.capture();
            this.eventPublisher.publishEvent(new InternalPaymentCapturedEvent(payment));
        }
        return notification;
    }

    @Override
    public Optional<Payment> getPayment(String id) {
        return CastUtils.cast(this.paymentRepository.findById(id));
    }

    @Override
    public Optional<String> getPaymentRedirectUrl(String id) {
        var payment = this.paymentRepository.findById(id).orElseThrow();
        return this.paymentClientFactory.getClient(payment).map(client -> client.createPaymentRedirectUrl(payment));
    }
}
