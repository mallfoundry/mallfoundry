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

import org.mallfoundry.finance.PaymentOrder;
import org.mallfoundry.finance.PaymentRefundException;
import org.mallfoundry.util.DecimalUtils;

import java.math.BigDecimal;

public abstract class PaymentOrderSupport implements MutablePaymentOrder {

    private void addRefundingAmount(BigDecimal deltaAmount) {
        this.setRefundingAmount(this.getRefundingAmount().add(deltaAmount));
    }

    private void addRefundedAmount(BigDecimal deltaAmount) {
        this.setRefundedAmount(this.getRefundedAmount().add(deltaAmount));
    }

    /**
     * 判断是否可以申请退款。
     */
    private boolean overApplyRefund(BigDecimal refundAmount) {
        var newRefundAmount = this.getRefundedAmount().add(this.getRefundingAmount()).add(refundAmount);
        return DecimalUtils.greaterThan(newRefundAmount, this.getAmount());
    }

    @Override
    public void applyRefund(BigDecimal refundAmount) throws PaymentRefundException {
        if (this.overApplyRefund(refundAmount)) {
            throw PaymentExceptions.Refund.overApply();
        }
        this.addRefundingAmount(refundAmount);
    }

    @Override
    public void succeedRefund(BigDecimal succeedAmount) throws PaymentRefundException {
        this.addRefundingAmount(BigDecimal.ZERO.subtract(succeedAmount));
        this.addRefundedAmount(succeedAmount);
    }

    @Override
    public void failRefund(BigDecimal failAmount) throws PaymentRefundException {
        this.addRefundingAmount(BigDecimal.ZERO.subtract(failAmount));
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        protected final PaymentOrderSupport order;

        protected BuilderSupport(PaymentOrderSupport order) {
            this.order = order;
        }

        @Override
        public Builder id(String id) {
            this.order.setId(id);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.order.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder amount(int amount) {
            return this.amount(BigDecimal.valueOf(amount));
        }

        @Override
        public Builder amount(double amount) {
            return this.amount(BigDecimal.valueOf(amount));
        }

        @Override
        public Builder amount(BigDecimal amount) {
            this.order.setAmount(amount);
            return this;
        }

        @Override
        public PaymentOrder build() {
            return this.order;
        }
    }
}
