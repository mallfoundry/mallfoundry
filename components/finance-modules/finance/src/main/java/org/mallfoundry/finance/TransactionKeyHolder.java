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

import org.mallfoundry.keygen.PrimaryKeyHolder;

public abstract class TransactionKeyHolder {

    private static final String WITHDRAWAL_ID_VALUE_NAME = "finance.withdrawal.id";

    private static final String TOPUP_ID_VALUE_NAME = "finance.topup.id";

    private static final String PAYMENT_ID_VALUE_NAME = "finance.payment.id";

    public static String nextKey(Class<?> clazz) throws TransactionException {
        if (Topup.class.isAssignableFrom(clazz)) {
            return TransactionType.TOPUP.code() + PrimaryKeyHolder.next(TOPUP_ID_VALUE_NAME);
        }
        if (Withdrawal.class.isAssignableFrom(clazz)) {
            return TransactionType.WITHDRAWAL.code() + PrimaryKeyHolder.next(WITHDRAWAL_ID_VALUE_NAME);
        }
        if (Payment.class.isAssignableFrom(clazz)) {
            return TransactionType.PAYMENT.code() + PrimaryKeyHolder.next(PAYMENT_ID_VALUE_NAME);
        }
        throw new TransactionException("Not support");
    }
}
