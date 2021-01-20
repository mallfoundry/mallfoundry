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

package org.mallfoundry.autoconfigure.security;

import org.mallfoundry.security.AuthorityRepository;
import org.mallfoundry.security.DefaultAuthorityService;
import org.mallfoundry.security.SecurityContextSystemUserSwitcher;
import org.mallfoundry.security.SecuritySubjectHolderStrategy;
import org.mallfoundry.security.SubjectHolder;
import org.mallfoundry.security.SubjectSwitches;
import org.mallfoundry.security.repository.jpa.JpaAuthorityRepository;
import org.mallfoundry.security.repository.jpa.DelegatingJpaAuthorityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SecurityAccessControlConfiguration.class)
public class SecurityAutoConfiguration {

    @Bean
    public JpaAuthorityRepository jpaAuthorityDescriptionRepository(DelegatingJpaAuthorityRepository repository) {
        return new JpaAuthorityRepository(repository);
    }

    @Bean
    public DefaultAuthorityService defaultAuthorityService(AuthorityRepository authorityDescriptionRepository) {
        return new DefaultAuthorityService(authorityDescriptionRepository);
    }

    @Bean
    public SecurityContextSystemUserSwitcher securityContextSystemUserSwitcher() {
        var switcher = new SecurityContextSystemUserSwitcher();
        SubjectSwitches.setSystemUserSwitcher(switcher);
        return switcher;
    }

    @Bean
    public SecuritySubjectHolderStrategy securitySubjectHolderStrategy() {
        var strategy = SecuritySubjectHolderStrategy.INSTANCE;
        SubjectHolder.setHolderStrategy(strategy);
        return strategy;
    }
}
