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

import org.mallfoundry.finance.account.BalanceTransaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mallfoundry.finance.WithdrawalStatus.CANCELED;
import static org.mallfoundry.finance.WithdrawalStatus.DISAPPROVED;
import static org.mallfoundry.finance.WithdrawalStatus.FAILED;
import static org.mallfoundry.finance.WithdrawalStatus.PENDING;
import static org.mallfoundry.finance.WithdrawalStatus.PROCESSING;
import static org.mallfoundry.finance.WithdrawalStatus.SUCCEEDED;

public abstract class WithdrawalSupport implements MutableWithdrawal {

    private void addApplyTransactions() {
        var transaction = this.createTransaction();
        transaction.setAccountId(this.getAccountId());
        transaction.setCurrencyCode(this.getCurrencyCode());
        transaction.setAmount(this.getAmount());
        transaction.setDirection(TransactionDirection.DEBIT);
        transaction.create(TransactionType.WITHDRAWAL);
        this.setTransactions(List.of(transaction));
    }

    @Override
    public void apply(List<BalanceTransaction> balanceTransactions) {
        this.setBalanceTransactions(balanceTransactions);
        this.setStatus(PENDING);
        this.setAppliedTime(new Date());
        this.addApplyTransactions();
    }

    @Override
    public void disapprove(String disapprovalReason) {
        if (!PENDING.equals(this.getStatus())) {
            throw new WithdrawalException(WithdrawalMessages.notDisapprove());
        }
        this.setDisapprovalReason(disapprovalReason);
        this.setDisapprovedTime(new Date());
        this.setStatus(DISAPPROVED);
    }

    @Override
    public void cancel() {
        if (!PENDING.equals(this.getStatus())) {
            throw new WithdrawalException(WithdrawalMessages.notCancel());
        }
        this.setCanceledTime(new Date());
        this.setStatus(CANCELED);
    }

    @Override
    public void approve() {
        this.setApprovedTime(new Date());
        this.setStatus(PROCESSING);
    }

    @Override
    public void succeed() {
        this.setSucceededTime(new Date());
        this.setStatus(SUCCEEDED);
    }

    @Override
    public void fail(String failureReason) {
        this.setFailureReason(failureReason);
        this.setFailedTime(new Date());
        this.setStatus(FAILED);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    private abstract static class BuilderSupport implements Builder {

        private final WithdrawalSupport withdrawal;

        protected BuilderSupport(WithdrawalSupport withdrawal) {
            this.withdrawal = withdrawal;
        }

        public Builder accountId(String accountId) {
            this.withdrawal.setAccountId(accountId);
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.withdrawal.setAmount(amount);
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.withdrawal.setCurrencyCode(currencyCode);
            return this;
        }

        public Builder recipient(Recipient recipient) {
            this.withdrawal.setRecipient(recipient);
            return this;
        }

        @Override
        public Withdrawal build() {
            return this.withdrawal;
        }
    }
}
