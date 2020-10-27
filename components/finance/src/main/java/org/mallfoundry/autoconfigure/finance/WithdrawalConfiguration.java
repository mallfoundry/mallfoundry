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

import org.mallfoundry.finance.DefaultWithdrawalService;
import org.mallfoundry.finance.RecipientService;
import org.mallfoundry.finance.WithdrawalAuthorizeProcessor;
import org.mallfoundry.finance.WithdrawalIdentityProcessor;
import org.mallfoundry.finance.WithdrawalProcessor;
import org.mallfoundry.finance.WithdrawalRepository;
import org.mallfoundry.finance.WithdrawalValidateProcessor;
import org.mallfoundry.finance.repository.jpa.DelegatingJpaWithdrawalRepository;
import org.mallfoundry.finance.repository.jpa.JpaWithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
public class WithdrawalConfiguration {

    @Bean
    public DelegatingJpaWithdrawalRepository delegatingJpaWithdrawalRepository(JpaWithdrawalRepository jpaWithdrawalRepository) {
        return new DelegatingJpaWithdrawalRepository(jpaWithdrawalRepository);
    }

    @Bean
    @Autowired(required = false)
    public DefaultWithdrawalService defaultWithdrawalService(@Lazy List<WithdrawalProcessor> processors,
                                                             RecipientService recipientService,
                                                             WithdrawalRepository withdrawalRepository) {
        var withdrawalService = new DefaultWithdrawalService(recipientService, withdrawalRepository);
        withdrawalService.setProcessors(processors);
        return withdrawalService;
    }

    @Bean
    public WithdrawalIdentityProcessor withdrawalIdentityProcessor() {
        return new WithdrawalIdentityProcessor();
    }

    @Bean
    public WithdrawalAuthorizeProcessor withdrawalAuthorizeProcessor() {
        return new WithdrawalAuthorizeProcessor();
    }

    @Bean
    public WithdrawalValidateProcessor withdrawalValidateProcessor() {
        return new WithdrawalValidateProcessor();
    }
}
