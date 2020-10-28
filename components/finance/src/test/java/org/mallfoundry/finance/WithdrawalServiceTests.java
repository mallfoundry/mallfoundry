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

import org.junit.jupiter.api.Test;
import org.mallfoundry.finance.bank.HolderType;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@StandaloneTest
public class WithdrawalServiceTests {

    @Autowired
    private WithdrawalService withdrawalService;

    @Test
    public void testApplyWithdrawal() {
        var withdrawal = this.withdrawalService.createWithdrawal(null)
                .toBuilder().currency("CNY").accountId("1").amount(BigDecimal.valueOf(4))
                .build();
        var recipient = withdrawal.createRecipient().toBuilder()
                .type(RecipientType.BANK_CARD).number("1101011")
                .name("name")
                .bankName("bank name").branchName("branch name")
                .holderType(HolderType.INDIVIDUAL).holderName("holder name")
                .build();
        withdrawal.setRecipient(recipient);
        this.withdrawalService.applyWithdrawal(withdrawal);
    }

    @Test
    public void testDisapproveWithdrawal() {
        this.withdrawalService.disapproveWithdrawal("3", "Disapprove");
    }
}
