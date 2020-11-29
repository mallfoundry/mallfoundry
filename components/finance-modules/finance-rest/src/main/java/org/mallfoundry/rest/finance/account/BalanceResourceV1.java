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

package org.mallfoundry.rest.finance.account;

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.finance.CurrencyCode;
import org.mallfoundry.finance.account.Balance;
import org.mallfoundry.finance.account.BalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class BalanceResourceV1 {

    private final BalanceService balanceService;

    public BalanceResourceV1(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/accounts/{account_id}/balances")
    public List<Balance> getBalances(@PathVariable(name = "account_id") String accountId) {
        return this.balanceService.getBalances(accountId);
    }

    @GetMapping("/accounts/{account_id}/balances/{currency_code}")
    public Balance getBalance(@PathVariable(name = "account_id") String accountId,
                              @PathVariable(name = "currency_code") String currency) {
        CurrencyCode currencyCode = CurrencyCode.valueOf(StringUtils.upperCase(currency));
        var balanceId = this.balanceService.createBalanceId(accountId, currencyCode);
        return this.balanceService.getBalance(balanceId);
    }
}
