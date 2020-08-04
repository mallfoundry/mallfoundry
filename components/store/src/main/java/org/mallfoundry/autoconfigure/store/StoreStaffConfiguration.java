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

import org.mallfoundry.store.role.StoreRoleService;
import org.mallfoundry.store.staff.DefaultStaffService;
import org.mallfoundry.store.staff.SmartStaffValidator;
import org.mallfoundry.store.staff.StaffAuthorizer;
import org.mallfoundry.store.staff.StaffIdentifier;
import org.mallfoundry.store.staff.StaffRepository;
import org.mallfoundry.store.staff.repository.jpa.JpaStaffRepository;
import org.mallfoundry.store.staff.repository.jpa.JpaStaffRepositoryDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.SmartValidator;

@Configuration
public class StoreStaffConfiguration {

    @Bean
    public JpaStaffRepository jpaStaffRepository(JpaStaffRepositoryDelegate repositoryDelegate) {
        return new JpaStaffRepository(repositoryDelegate);
    }

    @Bean
    public DefaultStaffService defaultStaffService(@Lazy StoreRoleService storeRoleService,
                                                   StaffRepository repository) {
        return new DefaultStaffService(storeRoleService, repository);
    }

    @Bean
    public StaffIdentifier staffIdentifier() {
        return new StaffIdentifier();
    }

    @Bean
    public StaffAuthorizer staffAuthorizer() {
        return new StaffAuthorizer();
    }

    @Bean
    public SmartStaffValidator smartStaffValidator(SmartValidator validator) {
        return new SmartStaffValidator(validator);
    }
}
