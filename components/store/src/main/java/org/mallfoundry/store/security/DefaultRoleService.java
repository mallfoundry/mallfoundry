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

package org.mallfoundry.store.security;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.configuration.ConfigurationHolder;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.util.Copies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DefaultRoleService implements RoleService, RoleProcessorInvoker, ApplicationEventPublisherAware {

    private List<RoleProcessor> processors = Collections.emptyList();

    private ApplicationEventPublisher eventPublisher;

    private final RoleRepository roleRepository;

    public DefaultRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setProcessors(List<RoleProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public RoleQuery createRoleQuery() {
        return new DefaultRoleQuery();
    }

    @Override
    public RoleId createRoleId(StoreId storeId, String roleId) {
        return new ImmutableRoleId(storeId.getTenantId(), storeId.getId(), roleId);
    }

    @Override
    public RoleId createRoleId(String storeId, String roleId) {
        return new ImmutableRoleId(null, storeId, roleId);
    }

    @Override
    public RoleId createRoleId(String roleId) {
        return new ImmutableRoleId(null, null, roleId);
    }

    @Override
    public Role createRole(RoleId roleId) {
        return this.roleRepository.create(roleId);
    }

    @Transactional
    @Override
    public Role addRole(Role role) {
        role.addStaff(null);
        role.custom();
        role = Function.<Role>identity()
                .compose(this.roleRepository::save)
                .compose(this::invokePreProcessBeforeAddRole)
                .apply(role);
        this.eventPublisher.publishEvent(new ImmutableRoleAddedEvent(role));
        return role;
    }

    private Role updateRole(Role source, Role target) {
        Copies.notBlank(source::getName).trim(target::setName);
        Copies.notBlank(source::getDescription).trim(target::setDescription);
        if (CollectionUtils.isNotEmpty(source.getAuthorities())) {
            target.setAuthorities(source.getAuthorities());
        }
        return target;
    }

    @Transactional
    @Override
    public Role updateRole(Role role) {
        return Function.<Role>identity()
                .compose(this.roleRepository::save)
                .compose(this::invokePreProcessAfterUpdateRole)
                .<Role>compose(target -> this.updateRole(role, target))
                .compose(this::invokePreProcessBeforeUpdateRole)
                .compose(this::getRole)
                .apply(this.createRoleId(role.getId()));
    }

    @Transactional
    @Override
    public void addRoleStaff(RoleId roleId, Staff staff) {
        var role = this.getRole(roleId);
        role.addStaff(this.invokePreProcessBeforeAddRoleStaff(role, staff));
        this.roleRepository.save(role);
    }

    @Transactional
    @Override
    public void removeRoleStaff(RoleId roleId, Staff staff) {
        var role = this.getRole(roleId);
        role.removeStaff(this.invokePreProcessBeforeRemoveRoleStaff(role, staff));
        this.roleRepository.save(role);
    }

    @Override
    public Role getRole(RoleId roleId) {
        return this.findRole(roleId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteRole(RoleId roleId) {
        var role = Function.<Role>identity()
                .compose(this::invokePreProcessBeforeDeleteRole)
                .compose(this::getRole)
                .apply(roleId);
        role = this.invokePreProcessAfterDeleteRole(role);
        this.roleRepository.delete(role);
        this.eventPublisher.publishEvent(new ImmutableRoleDeletedEvent(role));
    }

    @Override
    public void clearRoles(StoreId storeId) {
        var roles = this.roleRepository.findAllByStoreId(storeId);
        roles = this.invokePreProcessBeforeClearRoles(roles);
        // peek...
        roles = this.invokePreProcessAfterClearRoles(roles);
        this.roleRepository.deleteAll(roles);
        this.eventPublisher.publishEvent(new ImmutableRolesDeletedEvent(roles));
    }

    @Override
    public Role createSuperRole(StoreId storeId) {
        var roleId = this.createRoleId(storeId, null);
        return this.createRole(roleId)
                .toBuilder().authorities(List.of(AllAuthorities.STORE_MANAGE))
                .name(RoleMessages.SuperRole.name()).description(RoleMessages.SuperRole.description())
                .build();
    }

    @Transactional
    @Override
    public void changeSuperRole(RoleId roleId) {
        var config = ConfigurationHolder.getConfiguration(roleId);
        config.setProperty("store.roles.super_id", roleId.getId());
        ConfigurationHolder.saveConfiguration(config);
    }

    @Override
    public Role getSuperRole(StoreId storeId) {
        var config = ConfigurationHolder.getConfiguration(storeId);
        var roleId = config.getString("store.roles.super_id");
        var superId = this.createRoleId(storeId, roleId);
        return this.getRole(superId);
    }

    @Override
    public Optional<Role> findRole(RoleId roleId) {
        return this.roleRepository.findById(roleId);
    }

    @Override
    public List<Role> getRoles(Set<RoleId> roleIds) {
        return this.roleRepository.findAllById(roleIds);
    }

    @Override
    public SliceList<Role> getRoles(RoleQuery query) {
        return this.roleRepository.findAll(query);
    }

    @Override
    public Role invokePreProcessBeforeAddRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessBeforeAddRole)
                .apply(role);
    }

    @Override
    public Role invokePreProcessBeforeUpdateRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessBeforeUpdateRole)
                .apply(role);
    }

    @Override
    public Role invokePreProcessAfterUpdateRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessAfterUpdateRole)
                .apply(role);
    }

    @Override
    public Staff invokePreProcessBeforeAddRoleStaff(Role role, Staff staff) {
        return Processors.stream(this.processors)
                .<Staff>map((processor, identity) -> processor.preProcessBeforeAddRoleStaff(role, identity))
                .apply(staff);
    }

    @Override
    public Staff invokePreProcessBeforeRemoveRoleStaff(Role role, Staff staff) {
        return Processors.stream(this.processors)
                .<Staff>map((processor, identity) -> processor.preProcessBeforeRemoveRoleStaff(role, identity))
                .apply(staff);
    }

    @Override
    public Role invokePreProcessBeforeDeleteRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessBeforeDeleteRole)
                .apply(role);
    }

    @Override
    public Role invokePreProcessAfterDeleteRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessAfterDeleteRole)
                .apply(role);
    }

    @Override
    public List<Role> invokePreProcessBeforeClearRoles(List<Role> roles) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessBeforeClearRoles)
                .apply(roles);
    }

    @Override
    public List<Role> invokePreProcessAfterClearRoles(List<Role> roles) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessAfterClearRoles)
                .apply(roles);
    }
}
