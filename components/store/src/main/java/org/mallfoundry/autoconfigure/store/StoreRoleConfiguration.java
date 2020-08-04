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

import org.mallfoundry.store.role.DefaultStoreRoleService;
import org.mallfoundry.store.role.SmartStoreRoleValidator;
import org.mallfoundry.store.role.StoreRoleAuthorizer;
import org.mallfoundry.store.role.StoreRoleIdentifier;
import org.mallfoundry.store.role.StoreRoleProcessor;
import org.mallfoundry.store.role.StoreRoleRepository;
import org.mallfoundry.store.role.repository.jpa.JpaStoreRoleRepository;
import org.mallfoundry.store.role.repository.jpa.JpaStoreRoleRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.SmartValidator;

import java.util.List;

@Configuration
public class StoreRoleConfiguration {

    @Bean
    public JpaStoreRoleRepository jpaStoreRoleRepository(JpaStoreRoleRepositoryDelegate roleRepositoryDelegate) {
        return new JpaStoreRoleRepository(roleRepositoryDelegate);
    }


    @Bean
    @Autowired(required = false)
    public DefaultStoreRoleService defaultStoreRoleService(@Lazy List<StoreRoleProcessor> processors,
                                                           StoreRoleRepository repository) {
        var service = new DefaultStoreRoleService(repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public SmartStoreRoleValidator smartStoreRoleValidator(SmartValidator validator) {
        return new SmartStoreRoleValidator(validator);
    }

    @Bean
    public StoreRoleIdentifier storeRoleIdentifier() {
        return new StoreRoleIdentifier();
    }

    @Bean
    public StoreRoleAuthorizer storeRoleAuthorizer() {
        return new StoreRoleAuthorizer();
    }

}
