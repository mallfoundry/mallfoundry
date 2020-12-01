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

import static org.mallfoundry.finance.RechargeStatus.AWAITING_PAYMENT;
import static org.mallfoundry.finance.RechargeStatus.CANCELED;
import static org.mallfoundry.finance.RechargeStatus.FAILED;
import static org.mallfoundry.finance.RechargeStatus.PENDING;
import static org.mallfoundry.finance.RechargeStatus.SUCCEEDED;

public abstract class RechargeSupport implements MutableRecharge {

    @Override
    public void create() {
        this.setStatus(PENDING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void cancel() {
        this.setStatus(CANCELED);
        this.setCanceledTime(new Date());
    }

    @Override
    public void pay(PaymentSource source) {
        this.setSource(source);
        this.setStatus(AWAITING_PAYMENT);
    }

    @Override
    public void succeed() {
        this.setStatus(SUCCEEDED);
        this.setSucceededTime(new Date());
    }

    @Override
    public void fail(String failureReason) {
        this.setStatus(FAILED);
        this.setFailureReason(failureReason);
        this.setFailedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    private abstract static class BuilderSupport implements Builder {

        private final RechargeSupport recharge;

        protected BuilderSupport(RechargeSupport recharge) {
            this.recharge = recharge;
        }

        public Builder accountId(String accountId) {
            this.recharge.setAccountId(accountId);
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.recharge.setCurrencyCode(currencyCode);
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.recharge.setAmount(amount);
            return this;
        }

        @Override
        public Recharge build() {
            return this.recharge;
        }
    }
}
