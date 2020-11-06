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

package org.mallfoundry.finance.bank;

import java.util.Date;

public abstract class BankCardSupport implements MutableBankCard {

    @Override
    public void bind() {
        this.setBoundTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final BankCardSupport bankCard;

        protected BuilderSupport(BankCardSupport bankCard) {
            this.bankCard = bankCard;
        }

        @Override
        public Builder accountId(String accountId) {
            this.bankCard.setAccountId(accountId);
            return this;
        }

        @Override
        public Builder funding(BankCardFunding funding) {
            this.bankCard.setFunding(funding);
            return this;
        }

        @Override
        public Builder bankName(String bankName) {
            this.bankCard.setBankName(bankName);
            return this;
        }

        @Override
        public Builder branchName(String branchName) {
            this.bankCard.setBranchName(branchName);
            return this;
        }

        @Override
        public Builder holderType(HolderType holderType) {
            this.bankCard.setHolderType(holderType);
            return this;
        }

        @Override
        public Builder holderName(String holderName) {
            this.bankCard.setHolderName(holderName);
            return this;
        }

        @Override
        public Builder number(String number) {
            this.bankCard.setNumber(number);
            return this;
        }

        @Override
        public Builder last4(String last4) {
            this.bankCard.setLast4(last4);
            return this;
        }

        @Override
        public Builder expiryYear(String expiryYear) {
            this.bankCard.setExpiryYear(expiryYear);
            return this;
        }

        @Override
        public Builder expiryMonth(String expiryMonth) {
            this.bankCard.setExpiryMonth(expiryMonth);
            return this;
        }

        @Override
        public Builder phone(String phone) {
            this.bankCard.setPhone(phone);
            return this;
        }

        @Override
        public BankCard build() {
            return this.bankCard;
        }
    }
}
