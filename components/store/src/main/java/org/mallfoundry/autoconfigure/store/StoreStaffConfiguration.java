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

import org.mallfoundry.identity.UserService;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.DefaultStaffService;
import org.mallfoundry.store.staff.RoleStaffsCountProcessor;
import org.mallfoundry.store.staff.SmartStaffValidateProcessor;
import org.mallfoundry.store.staff.StaffAuthorizeProcessor;
import org.mallfoundry.store.staff.StaffProcessor;
import org.mallfoundry.store.staff.StaffRepository;
import org.mallfoundry.store.staff.repository.jpa.DelegatingJpaStaffRepository;
import org.mallfoundry.store.staff.repository.jpa.JpaStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.SmartValidator;

import java.util.List;

@Configuration
public class StoreStaffConfiguration {

    @Bean
    public DelegatingJpaStaffRepository delegatingJpaStaffRepository(JpaStaffRepository repositoryDelegate) {
        return new DelegatingJpaStaffRepository(repositoryDelegate);
    }

    @Bean
    public DefaultStaffService defaultStaffService(@Autowired(required = false)
                                                   @Lazy List<StaffProcessor> processors,
                                                   UserService userService,
                                                   RoleService storeRoleService,
                                                   StaffRepository repository) {
        var service = new DefaultStaffService(userService, storeRoleService, repository);
        service.setProcessors(processors);
        return service;
    }

    @Bean
    public StaffAuthorizeProcessor staffAuthorizeProcessor() {
        return new StaffAuthorizeProcessor();
    }

    @Bean
    public SmartStaffValidateProcessor smartStaffValidatedProcessor(SmartValidator validator) {
        return new SmartStaffValidateProcessor(validator);
    }

    @Bean
    public RoleStaffsCountProcessor roleStaffsCountProcessor(RoleService roleService) {
        return new RoleStaffsCountProcessor(roleService);
    }
}
