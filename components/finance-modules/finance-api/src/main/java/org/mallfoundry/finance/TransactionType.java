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

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    PAYMENT(1001), // 付款
    PAYMENT_REFUND(1002), // 付款退款
    TOPUP(1101), // 充值
    WITHDRAWAL(1201),  // 提现
    WITHDRAWAL_FEE(1202), // 提现服务费用
    TRANSFER(1301); // 转账

    private final int code;

    TransactionType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
