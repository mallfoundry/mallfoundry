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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ImmutableBalanceId implements BalanceId {

    private String accountId;

    private String currency;

    public ImmutableBalanceId(String accountId, String currency) {
        this.accountId = accountId;
        this.currency = currency;
    }

    public static ImmutableBalanceId of(BalanceId balanceId) {
        if (balanceId instanceof ImmutableBalanceId) {
            return (ImmutableBalanceId) balanceId;
        }
        return new ImmutableBalanceId(balanceId.getAccountId(), balanceId.getCurrency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImmutableBalanceId)) {
            return false;
        }
        ImmutableBalanceId that = (ImmutableBalanceId) o;
        return Objects.equals(accountId, that.accountId)
                && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, currency);
    }
}
