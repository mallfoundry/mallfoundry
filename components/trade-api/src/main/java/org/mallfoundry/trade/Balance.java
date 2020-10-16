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

package org.mallfoundry.trade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface Balance extends Serializable {

    String getCurrency();

    /**
     * 返回不可用余额。
     */
    BigDecimal getPendingAmount();

    /**
     * 返回可用余额。
     */
    BigDecimal getAvailableAmount();

    BalanceSource createSource(SourceType sourceType);

    BalanceSource getSource(SourceType sourceType);

    /**
     * 返回余额来源集合，余额来源是存放在第三方支付平台的余额。
     */
    List<BalanceSource> getSources();

    /**
     * 存钱。
     */
    void credit(SourceType type, BigDecimal amount);

    /**
     * 取钱。
     */
    void debit(SourceType type, BigDecimal amount);

    void freeze(BigDecimal amount);

    void unfreeze(BigDecimal amount);
}
