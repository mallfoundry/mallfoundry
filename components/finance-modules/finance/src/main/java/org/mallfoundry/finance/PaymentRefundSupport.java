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

import java.math.BigDecimal;
import java.util.Date;

import static org.mallfoundry.finance.PaymentRefundStatus.FAILED;
import static org.mallfoundry.finance.PaymentRefundStatus.PENDING;
import static org.mallfoundry.finance.PaymentRefundStatus.SUCCEEDED;

public abstract class PaymentRefundSupport implements MutablePaymentRefund {

    @Override
    public boolean isSucceeded() {
        return SUCCEEDED == this.getStatus();
    }

    @Override
    public boolean isFailed() {
        return FAILED == this.getStatus();
    }

    @Override
    public void apply() {
        this.setAppliedTime(new Date());
        this.setStatus(PENDING);
    }

    @Override
    public void succeed() {
        this.setSucceededTime(new Date());
        this.setStatus(SUCCEEDED);
    }

    @Override
    public void fail(String failReason) {
        this.setFailReason(failReason);
        this.setFailedTime(new Date());
        this.setStatus(FAILED);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final PaymentRefundSupport refund;

        protected BuilderSupport(PaymentRefundSupport refund) {
            this.refund = refund;
        }

        @Override
        public Builder id(String id) {
            this.refund.setId(id);
            return this;
        }

        @Override
        public Builder orderId(String orderId) {
            this.refund.setOrderId(orderId);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.refund.setStoreId(storeId);
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
            this.refund.setAmount(amount);
            return this;
        }

        @Override
        public Builder reason(String reason) {
            this.refund.setReason(reason);
            return this;
        }

        @Override
        public PaymentRefund build() {
            return this.refund;
        }
    }
}
