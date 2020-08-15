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

package org.mallfoundry.autoconfigure.store;

import org.mallfoundry.store.StoreService;
import org.mallfoundry.store.security.DefaultRoleService;
import org.mallfoundry.store.security.RoleAccessControlEventListener;
import org.mallfoundry.store.security.RoleAuthorizeProcessor;
import org.mallfoundry.store.security.RoleIdentityProcessor;
import org.mallfoundry.store.security.RoleProcessor;
import org.mallfoundry.store.security.RoleRepository;
import org.mallfoundry.store.security.RoleValidateProcessor;
import org.mallfoundry.store.security.repository.jpa.DelegatingJpaRoleRepository;
import org.mallfoundry.store.security.repository.jpa.JpaRoleRepository;
import org.mallfoundry.store.security.repository.jpa.JpaStaffRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Import(RoleAccessControlEventListener.class)
@Configuration
public class StoreSecurityConfiguration {

    @Bean
    public DelegatingJpaRoleRepository delegatingJpaRoleRepository(JpaRoleRepository roleRepository,
                                                                   JpaStaffRoleRepository staffRoleRepository) {
        return new DelegatingJpaRoleRepository(roleRepository, staffRoleRepository);
    }

    @Bean
    public DefaultRoleService defaultStoreRoleService(@Autowired(required = false)
                                                      @Lazy List<RoleProcessor> processors,
                                                      StoreService storeService,
                                                      RoleRepository repository) {
        var service = new DefaultRoleService(storeService, repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public RoleValidateProcessor roleValidateProcessor() {
        return new RoleValidateProcessor();
    }

    @Bean
    public RoleIdentityProcessor storeRoleIdentityProcessor() {
        return new RoleIdentityProcessor();
    }

    @Bean
    public RoleAuthorizeProcessor storeRoleAuthorizeProcessor() {
        return new RoleAuthorizeProcessor();
    }
}
