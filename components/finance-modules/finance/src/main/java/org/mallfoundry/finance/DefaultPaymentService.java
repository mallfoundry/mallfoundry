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

package org.mallfoundry.finance;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.thirdpay.PaymentClientFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

public class DefaultPaymentService implements PaymentService, ApplicationEventPublisherAware {

    private static final String PAYMENT_ID_VALUE_NAME = "payment.id";

    private final PaymentRepository paymentRepository;

    private final PaymentClientFactory clientFactory;

    private ApplicationEventPublisher eventPublisher;

    public DefaultPaymentService(PaymentRepository paymentRepository, PaymentClientFactory clientFactory) {
        this.paymentRepository = paymentRepository;
        this.clientFactory = clientFactory;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Payment createPayment(String id) {
        return this.paymentRepository.create(id);
    }

    private Payment requiredPayment(String id) {
        return this.paymentRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public Payment startPayment(Payment payment) {
        payment.setId(PrimaryKeyHolder.next(PAYMENT_ID_VALUE_NAME));
        payment.start();
        var savedPayment = this.paymentRepository.save(payment);
        this.eventPublisher.publishEvent(new ImmutablePaymentStartedEvent(savedPayment));
        return savedPayment;
    }

    private void capturePayment(Payment payment) throws PaymentException {
        payment.capture();
        var savedPayment = this.paymentRepository.save(payment);
        this.eventPublisher.publishEvent(new ImmutablePaymentCapturedEvent(savedPayment));
    }

    @Transactional
    @Override
    public void capturePayment(String id) throws PaymentException {
        var payment = this.requiredPayment(id);
        this.capturePayment(payment);
    }

    @Transactional
    @Override
    public PaymentRefund refundPayment(String id, PaymentRefund newRefund) {
        var payment = this.requiredPayment(id);
        var refund = payment.applyRefund(newRefund);
        var result = this.clientFactory.getClient(payment).refundPayment(payment, refund);
        // 如果不为空，设置退款交易号
        if (StringUtils.isNotBlank(result.getSourceId())) {
            refund.setSourceId(result.getSourceId());
        }
        if (result.isSucceeded()) {
            refund.succeed();
        } else if (result.isFailed()) {
            refund.fail(refund.getFailReason());
        }
        this.paymentRepository.save(payment);
        return refund;
    }

    @Transactional
    @Override
    public PaymentNotification notifyPayment(String id, Object parameters) {
        var payment = this.requiredPayment(id);
        var notification = this.clientFactory.getClient(payment).validateNotification(parameters);
        if (notification.isCaptured() && payment.isStarted()) {
            // 设置支付源交易标识
            payment.setSourceId(notification.getSourceId());
            this.capturePayment(payment);
        }
        return notification;
    }

    @Override
    public Optional<Payment> getPayment(String id) {
        return this.paymentRepository.findById(id);
    }

    @Transactional
    @Override
    public String redirectPaymentUrl(String id) {
        var payment = this.requiredPayment(id);
        return this.clientFactory.getClient(payment).redirectPaymentUrl(payment);
    }

    @Override
    public String returnPaymentUrl(String id) {
        var payment = this.requiredPayment(id);
        return UriComponentsBuilder.fromHttpUrl(payment.getReturnUrl()).build(Map.of("payment_id", payment.getId())).toString();
    }
}
