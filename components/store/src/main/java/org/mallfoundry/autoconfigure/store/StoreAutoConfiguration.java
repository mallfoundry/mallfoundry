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

import org.mallfoundry.store.DefaultStoreAddressService;
import org.mallfoundry.store.DefaultStoreCollectionService;
import org.mallfoundry.store.DefaultStoreConfiguration;
import org.mallfoundry.store.StoreAddressRepository;
import org.mallfoundry.store.StoreCollectionRepository;
import org.mallfoundry.store.StoreConfiguration;
import org.mallfoundry.store.repository.jpa.JpaStoreAddressRepository;
import org.mallfoundry.store.repository.jpa.JpaStoreAddressRepositoryDelegate;
import org.mallfoundry.store.repository.jpa.JpaStoreCollectionRepository;
import org.mallfoundry.store.repository.jpa.JpaStoreCollectionRepositoryDelegate;
import org.mallfoundry.store.role.DefaultStoreRoleService;
import org.mallfoundry.store.role.SmartStoreRoleValidator;
import org.mallfoundry.store.role.StoreRoleAuthorizer;
import org.mallfoundry.store.role.StoreRoleIdentifier;
import org.mallfoundry.store.role.StoreRoleProcessor;
import org.mallfoundry.store.role.StoreRoleProcessorsInvoker;
import org.mallfoundry.store.role.StoreRoleRepository;
import org.mallfoundry.store.role.StoreRoleService;
import org.mallfoundry.store.role.repository.jpa.JpaStoreRoleRepository;
import org.mallfoundry.store.role.repository.jpa.JpaStoreRoleRepositoryDelegate;
import org.mallfoundry.store.staff.DefaultStaffService;
import org.mallfoundry.store.staff.SmartStaffValidator;
import org.mallfoundry.store.staff.StaffAuthorizer;
import org.mallfoundry.store.staff.StaffIdentifier;
import org.mallfoundry.store.staff.StaffProcessor;
import org.mallfoundry.store.staff.StaffProcessorsInvoker;
import org.mallfoundry.store.staff.StaffRepository;
import org.mallfoundry.store.staff.repository.jpa.JpaStaffRepository;
import org.mallfoundry.store.staff.repository.jpa.JpaStaffRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.SmartValidator;

import java.util.List;

@Configuration
@EnableConfigurationProperties(StoreProperties.class)
public class StoreAutoConfiguration {

    @Bean
    public StoreConfiguration storeConfiguration(StoreProperties properties) {
        var config = new DefaultStoreConfiguration();
        config.setDefaultLogo(properties.getDefaultLogo());
        return config;
    }

    @Bean
    public JpaStoreRoleRepository jpaStoreRoleRepository(JpaStoreRoleRepositoryDelegate roleRepositoryDelegate) {
        return new JpaStoreRoleRepository(roleRepositoryDelegate);
    }

    @Bean
    @Autowired(required = false)
    public StoreRoleProcessorsInvoker storeRoleProcessorsInvoker(@Lazy List<StoreRoleProcessor> processors) {
        return new StoreRoleProcessorsInvoker(processors);
    }

    @Bean
    public DefaultStoreRoleService defaultStoreRoleService(StoreRoleProcessorsInvoker processorsInvoker,
                                                           StoreRoleRepository repository) {
        return new DefaultStoreRoleService(processorsInvoker, repository);
    }

    @Bean
    public JpaStaffRepository jpaStaffRepository(JpaStaffRepositoryDelegate repositoryDelegate) {
        return new JpaStaffRepository(repositoryDelegate);
    }

    @Bean
    @Autowired(required = false)
    public StaffProcessorsInvoker staffProcessorsInvoker(@Lazy List<StaffProcessor> processors) {
        return new StaffProcessorsInvoker(processors);
    }

    @Bean
    public DefaultStaffService defaultStaffService(StaffProcessorsInvoker processorInvoker,
                                                   @Lazy StoreRoleService storeRoleService,
                                                   StaffRepository repository) {
        return new DefaultStaffService(processorInvoker, storeRoleService, repository);
    }

    @Bean
    public JpaStoreAddressRepository jpaStoreAddressRepository(JpaStoreAddressRepositoryDelegate repositoryDelegate) {
        return new JpaStoreAddressRepository(repositoryDelegate);
    }

    @Bean
    public DefaultStoreAddressService defaultStoreAddressService(StoreAddressRepository repository) {
        return new DefaultStoreAddressService(repository);
    }

    @Bean
    public JpaStoreCollectionRepository jpaStoreCollectionRepository(JpaStoreCollectionRepositoryDelegate repositoryDelegate) {
        return new JpaStoreCollectionRepository(repositoryDelegate);
    }

    @Bean
    public DefaultStoreCollectionService defaultStoreCollectionService(StoreCollectionRepository repository) {
        return new DefaultStoreCollectionService(repository);
    }

    @Bean
    public StoreRoleIdentifier storeRoleIdentifier() {
        return new StoreRoleIdentifier();
    }

    @Bean
    public StoreRoleAuthorizer storeRoleAuthorizer() {
        return new StoreRoleAuthorizer();
    }

    @Bean
    public SmartStoreRoleValidator smartStoreRoleValidator(SmartValidator validator) {
        return new SmartStoreRoleValidator(validator);
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
