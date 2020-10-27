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

import org.mallfoundry.finance.WithdrawalException;
import org.mallfoundry.util.DecimalUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class DefaultBalanceService implements BalanceService {

    private final BalanceRepository balanceRepository;

    private final BalanceTransactionService balanceTransactionService;

    public DefaultBalanceService(BalanceRepository balanceRepository,
                                 BalanceTransactionService balanceTransactionService) {
        this.balanceRepository = balanceRepository;
        this.balanceTransactionService = balanceTransactionService;
    }

    @Override
    public BalanceId createBalanceId(String accountId, String currency) {
        return new ImmutableBalanceId(accountId, currency);
    }

    @Override
    public Balance createBalance(BalanceId balanceId) {
        return this.balanceRepository.create(balanceId);
    }

    private Balance getBalance(BalanceId balanceId) {
        return this.balanceRepository.findById(balanceId).orElse(null);
    }

    private Balance requiredBalance(BalanceId balanceId) {
        var balance = this.getBalance(balanceId);
        if (Objects.isNull(balance)) {
            balance = this.createBalance(balanceId);
        }
        return balance;
    }

    @Transactional
    @Override
    public BalanceSource rechargeBalance(BalanceId balanceId, BalanceSourceType sourceType, BigDecimal amount) {
        var balance = this.requiredBalance(balanceId);
        var source = balance.credit(sourceType, amount);
        this.balanceRepository.save(balance);
        return source;
    }


    @Transactional
    @Override
    public List<BalanceTransaction> withdrawBalance(BalanceId balanceId, BigDecimal amount) {
        var balance = this.requiredBalance(balanceId);
        // withdrawal amount >= availableAmount
        if (DecimalUtils.greaterThanOrEqualTo(amount, balance.getAvailableAmount())) {
            throw new WithdrawalException("The withdrawal amount exceeds the available amount");
        }
        var transaction = balance.withdraw(amount);
        this.balanceRepository.save(balance);
        return this.balanceTransactionService.saveAll(List.of(transaction));
    }
}
