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

import lombok.Setter;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DefaultAccountService implements AccountService, AccountProcessorInvoker {

    @Setter
    private List<AccountProcessor> processors;

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
    public Account openAccount(Account account) {
        account = this.invokePreProcessBeforeOpenAccount(account);
        account.open();
        account = this.invokePreProcessAfterOpenAccount(account);
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

    @Override
    public Account invokePreProcessBeforeOpenAccount(Account account) {
        return Processors.stream(this.processors)
                .map(AccountProcessor::preProcessBeforeOpenAccount)
                .apply(account);
    }

    @Override
    public Account invokePreProcessAfterOpenAccount(Account account) {
        return Processors.stream(this.processors)
                .map(AccountProcessor::preProcessAfterOpenAccount)
                .apply(account);
    }
}
