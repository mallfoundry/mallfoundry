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

import org.mallfoundry.finance.DefaultTopupService;
import org.mallfoundry.finance.TopupIdentityProcessor;
import org.mallfoundry.finance.TopupProcessor;
import org.mallfoundry.finance.TopupRepository;
import org.mallfoundry.finance.repository.jpa.DelegatingJpaTopupRepository;
import org.mallfoundry.finance.repository.jpa.JpaTopupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TopupAutoConfiguration {

    @Bean
    public DelegatingJpaTopupRepository delegatingJpaTopupRepository(JpaTopupRepository repository) {
        return new DelegatingJpaTopupRepository(repository);
    }

    @Bean
    public DefaultTopupService defaultTopupService(List<TopupProcessor> processors,
                                                   TopupRepository rechargeRepository) {
        var service = new DefaultTopupService(rechargeRepository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public TopupIdentityProcessor topupIdentityProcessor() {
        return new TopupIdentityProcessor();
    }
}
