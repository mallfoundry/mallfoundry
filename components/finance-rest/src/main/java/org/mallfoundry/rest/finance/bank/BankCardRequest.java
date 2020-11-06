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

package org.mallfoundry.rest.finance.bank;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.finance.bank.BankCard;
import org.mallfoundry.finance.bank.BankCardFunding;
import org.mallfoundry.finance.bank.HolderType;

@Getter
@Setter
public class BankCardRequest {

    private String accountId;

    private BankCardFunding funding;

    private String bankName;

    private String branchName;

    private HolderType holderType;

    private String holderName;

    private String number;

    private String last4;

    private String expiryYear;

    private String expiryMonth;

    private String phone;

    public BankCard assignTo(BankCard bankCard) {
        return bankCard.toBuilder()
                .accountId(this.accountId).funding(this.funding)
                .bankName(this.bankName).branchName(this.branchName)
                .holderType(this.holderType).holderName(this.holderName)
                .number(this.number).last4(this.last4)
                .expiryYear(this.expiryYear).expiryMonth(this.expiryMonth)
                .phone(this.phone)
                .build();
    }
}
