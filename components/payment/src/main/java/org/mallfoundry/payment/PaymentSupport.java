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

import org.apache.commons.collections4.ListUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mallfoundry.payment.PaymentStatus.CAPTURED;
import static org.mallfoundry.payment.PaymentStatus.PENDING;

public abstract class PaymentSupport implements MutablePayment {

    @Override
    public boolean isStarted() {
        return PENDING.equals(this.getStatus());
    }

    @Override
    public boolean isCaptured() {
        return CAPTURED.equals(this.getStatus());
    }

    @Override
    public void start() {
        if (Objects.nonNull(this.getStatus())) {
            throw PaymentExceptions.started();
        }
        this.setStatus(PENDING);
        this.setStartedTime(new Date());
    }

    @Override
    public void capture() {
        if (!PENDING.equals(this.getStatus())) {
            throw PaymentExceptions.cannotCaptured();
        }
        this.setStatus(CAPTURED);
        this.setCapturedTime(new Date());
    }

    private void addTotalAmount(BigDecimal deltaAmount) {
        this.setTotalAmount(this.getTotalAmount().add(deltaAmount));
    }

    @Override
    public void addOrder(PaymentOrder order) {
        if (Objects.nonNull(this.getStatus())) {
            throw PaymentExceptions.cannotAddOrder();
        }
        this.getOrders().add(order);
        this.addTotalAmount(order.getAmount());
    }

    @Override
    public void addOrders(List<PaymentOrder> orders) {
        ListUtils.emptyIfNull(orders).forEach(this::addOrder);
    }

    @Override
    public Optional<PaymentOrder> getOrder(String orderId) {
        return this.getOrders()
                .stream()
                .filter(order -> Objects.equals(order.getId(), orderId))
                .findFirst();
    }

    private PaymentOrder requiredOrder(String orderId) {
        return this.getOrder(orderId).orElseThrow(PaymentExceptions.Order::notFound);
    }

    @Override
    public Optional<PaymentRefund> getRefund(String refundId) {
        return this.getRefunds().stream()
                .filter(refund -> Objects.equals(refund.getId(), refundId))
                .findFirst();
    }

    public PaymentRefund requiredRefund(String refundId) {
        return this.getRefund(refundId).orElseThrow(PaymentExceptions.Refund::notFound);
    }

    private boolean existsRefund(String refundId) {
        return this.getRefund(refundId).isPresent();
    }

    @Override
    public PaymentRefund applyRefund(PaymentRefund refund) {
        if (!isCaptured()) {
            throw PaymentExceptions.cannotApplyRefund();
        }
        if (this.existsRefund(refund.getId())) {
            throw PaymentExceptions.Refund.duplicate();
        }
        var order = this.requiredOrder(refund.getOrderId());
        // 设置商铺标识
        refund.setStoreId(order.getStoreId());
        order.applyRefund(refund.getAmount());
        refund.apply();
        this.getRefunds().add(refund);
        return refund;
    }

    @Override
    public void succeedRefund(String refundId) {
        var refund = this.requiredRefund(refundId);
        var order = this.requiredOrder(refund.getOrderId());
        order.succeedRefund(refund.getAmount());
        refund.succeed();
    }

    @Override
    public void failRefund(String refundId, String failReason) {
        var refund = this.requiredRefund(refundId);
        var order = this.requiredOrder(refund.getOrderId());
        order.failRefund(refund.getAmount());
        refund.fail(failReason);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        protected final PaymentSupport payment;

        protected BuilderSupport(PaymentSupport payment) {
            this.payment = payment;
        }

        @Override
        public Builder payerId(String payerId) {
            this.payment.setPayerId(payerId);
            return this;
        }

        @Override
        public Builder payer(String payer) {
            this.payment.setPayer(payer);
            return this;
        }

        @Override
        public Builder returnUrl(String returnUrl) {
            return null;
        }

        @Override
        public Builder instrument(PaymentInstrument instrument) {
            this.payment.setInstrument(instrument);
            return this;
        }

        @Override
        public Builder instrument(Consumer<PaymentInstrument> consumer) {
            var instrument = this.payment.createInstrument(null);
            consumer.accept(instrument);
            return this.instrument(instrument);
        }

        @Override
        public Builder instrument(Function<Payment, PaymentInstrument> function) {
            return this.instrument(function.apply(this.payment));
        }

        @Override
        public Builder sourceId(String sourceId) {
            return null;
        }

        @Override
        public Builder order(PaymentOrder order) {
            this.payment.addOrder(order);
            return this;
        }

        @Override
        public Builder order(Consumer<PaymentOrder> consumer) {
            var order = this.payment.createOrder(null);
            consumer.accept(order);
            return this.order(order);
        }

        @Override
        public Builder order(Function<Payment, PaymentOrder> function) {
            return this.order(function.apply(this.payment));
        }

        @Override
        public Payment build() {
            return this.payment;
        }
    }
}
