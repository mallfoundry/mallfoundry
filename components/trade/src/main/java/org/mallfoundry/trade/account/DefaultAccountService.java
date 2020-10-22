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

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class DefaultAccountService implements AccountService {

    private final AccountRepository accountRepository;

    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String id) {
        return this.accountRepository.create(id);
    }

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountId) throws AccountException {
        return this.requiredAccount(accountId);
    }

    private Account requiredAccount(String accountId) throws AccountException {
        return this.accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountException(AccountMessages.notFound()));
    }

    @Transactional
    @Override
    public Balance creditAccountBalance(String accountId, String currency, BalanceSourceType type, BigDecimal amount) {
        var account = this.requiredAccount(accountId);
        var balance = account.credit(currency, type, amount);
        this.accountRepository.save(account);
        return balance;
    }

    @Transactional
    @Override
    public Balance debitAccountBalance(String accountId, String currency, BalanceSourceType type, BigDecimal amount) {
        var account = this.requiredAccount(accountId);
        var balance = account.debit(currency, type, amount);
        this.accountRepository.save(account);
        return balance;
    }

    @Transactional
    @Override
    public Balance freezeAccountBalance(String accountId, String currency, BigDecimal amount) {
        var account = this.requiredAccount(accountId);
        var balance = account.freeze(currency, amount);
        this.accountRepository.save(account);
        return balance;
    }

    @Transactional
    @Override
    public Balance unfreezeAccountBalance(String accountId, String currency, BigDecimal amount) {
        var account = this.requiredAccount(accountId);
        var balance = account.unfreeze(currency, amount);
        this.accountRepository.save(account);
        return balance;
    }
}
