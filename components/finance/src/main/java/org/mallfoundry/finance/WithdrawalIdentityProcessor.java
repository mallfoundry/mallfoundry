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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.keygen.PrimaryKeyHolder;

public class WithdrawalIdentityProcessor implements WithdrawalProcessor {

    private static final String WITHDRAWAL_ID_VALUE_NAME = "finance.withdrawal.id";

    private static final String TRANSACTION_ID_VALUE_NAME = "finance.transaction.id";

    @Override
    public Withdrawal preProcessAfterApplyWithdrawal(Withdrawal withdrawal) {
        if (StringUtils.isBlank(withdrawal.getId())) {
            withdrawal.setId(this.nextWithdrawalId());
        }
        withdrawal.getTransactions().forEach(transaction -> transaction.setId(PrimaryKeyHolder.next(TRANSACTION_ID_VALUE_NAME)));
        return withdrawal;
    }

    private String nextWithdrawalId() {
        return "1005" + PrimaryKeyHolder.next(WITHDRAWAL_ID_VALUE_NAME);
    }
}
