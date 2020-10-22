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

package org.mallfoundry.trade.account;

import org.mallfoundry.trade.SourceType;

import java.math.BigDecimal;
import java.util.List;

public interface Account {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    BusinessType getBusinessType();

    void setBusinessType(BusinessType businessType);

    Balance createBalance(String currency);

    List<Balance> getBalances();

    Balance getBalance(String currency);

    Balance credit(String currency, SourceType type, BigDecimal amount);

    Balance debit(String currency, SourceType type, BigDecimal amount);

    Balance freeze(String currency, BigDecimal amount);

    Balance unfreeze(String currency, BigDecimal amount);
}
