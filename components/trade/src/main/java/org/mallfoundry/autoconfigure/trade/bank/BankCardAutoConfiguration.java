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

package org.mallfoundry.autoconfigure.trade.bank;

import org.mallfoundry.trade.bank.BankCardRepository;
import org.mallfoundry.trade.bank.DefaultBankCardService;
import org.mallfoundry.trade.bank.repository.jpa.DelegatingJpaBankCardRepository;
import org.mallfoundry.trade.bank.repository.jpa.JpaBankCardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankCardAutoConfiguration {

    @Bean
    public DelegatingJpaBankCardRepository delegatingJpaBankCardRepository(JpaBankCardRepository repository) {
        return new DelegatingJpaBankCardRepository(repository);
    }

    @Bean
    public DefaultBankCardService defaultBankCardService(BankCardRepository bankCardRepository) {
        return new DefaultBankCardService(bankCardRepository);
    }
}
