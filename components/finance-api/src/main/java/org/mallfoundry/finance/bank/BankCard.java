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

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface BankCard extends ObjectBuilder.ToBuilder<BankCard.Builder> {

    String getId();

    void setId(String id);

    String getAccountId();

    void setAccountId(String accountId);

    BankCardFunding getFunding();

    void setFunding(BankCardFunding funding);

    String getBankName();

    void setBankName(String bankName);

    // 支行名称
    String getBranchName();

    void setBranchName(String branchName);

    HolderType getHolderType();

    void setHolderType(HolderType holderType);

    String getHolderName();

    void setHolderName(String holderName);

    String getNumber();

    void setNumber(String number);

    String getLast4();

    void setLast4(String last4);

    String getExpiryYear();

    void setExpiryYear(String expiryYear);

    String getExpiryMonth();

    void setExpiryMonth(String expiryMonth);

    Date getBoundTime();

    void bind();

    interface Builder extends ObjectBuilder<BankCard> {

        Builder accountId(String accountId);

        Builder funding(BankCardFunding funding);

        Builder bankName(String bankName);

        Builder branchName(String branchName);

        Builder holderType(HolderType holderType);

        Builder holderName(String holderName);

        Builder number(String number);

        Builder last4(String last4);

        Builder expiryYear(String expiryYear);

        Builder expiryMonth(String expiryMonth);

    }
}
