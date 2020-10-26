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

import org.mallfoundry.finance.bank.HolderType;

public abstract class RecipientSupport implements Recipient {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final Recipient recipient;

        protected BuilderSupport(Recipient recipient) {
            this.recipient = recipient;
        }

        @Override
        public Builder number(String number) {
            this.recipient.setNumber(number);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.recipient.setName(name);
            return this;
        }

        @Override
        public Builder type(RecipientType type) {
            this.recipient.setType(type);
            return this;
        }

        @Override
        public Builder bankName(String bankName) {
            this.recipient.setBankName(bankName);
            return this;
        }

        @Override
        public Builder branchName(String branchName) {
            this.recipient.setBranchName(branchName);
            return this;
        }

        @Override
        public Builder holderType(HolderType holderType) {
            this.recipient.setHolderType(holderType);
            return this;
        }

        @Override
        public Builder holderName(String holderName) {
            this.recipient.setHolderName(holderName);
            return this;
        }

        @Override
        public Recipient build() {
            return this.recipient;
        }
    }
}
