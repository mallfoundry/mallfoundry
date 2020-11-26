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

import org.mallfoundry.finance.DefaultRechargeService;
import org.mallfoundry.finance.RechargeIdentityProcessor;
import org.mallfoundry.finance.RechargeProcessor;
import org.mallfoundry.finance.RechargeRepository;
import org.mallfoundry.finance.repository.jpa.DelegatingJpaRechargeRepository;
import org.mallfoundry.finance.repository.jpa.JpaRechargeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RechargeAutoConfiguration {

    @Bean
    public DelegatingJpaRechargeRepository delegatingJpaRechargeRepository(JpaRechargeRepository repository) {
        return new DelegatingJpaRechargeRepository(repository);
    }

    @Bean
    public DefaultRechargeService defaultRechargeService(List<RechargeProcessor> processors,
                                                         RechargeRepository rechargeRepository) {
        var service = new DefaultRechargeService(rechargeRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public RechargeIdentityProcessor rechargeIdentityProcessor() {
        return new RechargeIdentityProcessor();
    }
}
