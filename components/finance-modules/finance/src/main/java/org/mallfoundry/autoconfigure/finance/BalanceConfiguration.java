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

package org.mallfoundry.autoconfigure.finance;

import org.mallfoundry.finance.account.BalanceRepository;
import org.mallfoundry.finance.account.BalanceTransactionRepository;
import org.mallfoundry.finance.account.BalanceTransactionService;
import org.mallfoundry.finance.account.DefaultBalanceService;
import org.mallfoundry.finance.account.repository.jpa.DelegatingJpaBalanceRepository;
import org.mallfoundry.finance.account.repository.jpa.DelegatingJpaBalanceTransactionRepository;
import org.mallfoundry.finance.account.repository.jpa.JpaBalanceRepository;
import org.mallfoundry.finance.account.repository.jpa.JpaBalanceTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceConfiguration {

    @Bean
    public DelegatingJpaBalanceTransactionRepository delegatingJpaBalanceTransactionRepository(JpaBalanceTransactionRepository repository) {
        return new DelegatingJpaBalanceTransactionRepository(repository);
    }

    @Bean
    public BalanceTransactionService balanceTransactionService(BalanceTransactionRepository balanceTransactionRepository) {
        return new BalanceTransactionService(balanceTransactionRepository);
    }

    @Bean
    public DelegatingJpaBalanceRepository delegatingJpaBalanceRepository(JpaBalanceRepository repository) {
        return new DelegatingJpaBalanceRepository(repository);
    }

    @Bean
    public DefaultBalanceService defaultBalanceService(BalanceRepository balanceRepository,
                                                       BalanceTransactionService balanceTransactionService) {
        return new DefaultBalanceService(balanceRepository, balanceTransactionService);
    }
}
