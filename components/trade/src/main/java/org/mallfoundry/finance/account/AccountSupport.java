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

package org.mallfoundry.finance.account;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class AccountSupport implements MutableAccount {

    @Override
    public Balance getBalance(String currency) {
        return this.getBalances().stream()
                .filter(balance -> balance.getCurrency().equals(currency))
                .findFirst().orElse(null);
    }

    @Override
    public Balance credit(String currency, BalanceSourceType type, BigDecimal amount) {
        var balance = this.getBalance(currency);
        if (Objects.isNull(balance)) {
            balance = this.createBalance(currency);
            this.getBalances().add(balance);
        }
        balance.credit(type, amount);
        return balance;
    }

    @Override
    public Balance debit(String currency, BalanceSourceType sourceType, BigDecimal amount) {
        var balance = this.getBalance(currency);
        if (Objects.isNull(balance)) {
            balance = this.createBalance(currency);
            this.getBalances().add(balance);
        }
        balance.debit(sourceType, amount);
        return balance;
    }

    @Override
    public Balance freeze(String currency, BigDecimal amount) {
        var balance = this.getBalance(currency);
        balance.freeze(amount);
        return balance;
    }

    @Override
    public Balance unfreeze(String currency, BigDecimal amount) {
        var balance = this.getBalance(currency);
        balance.unfreeze(amount);
        return balance;
    }
}
