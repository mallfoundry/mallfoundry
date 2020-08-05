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

package org.mallfoundry.store.role;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class DefaultRoleService implements RoleService, RoleProcessorInvoker {

    private List<RoleProcessor> processors = Collections.emptyList();

    private final RoleRepository roleRepository;

    public DefaultRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void setProcessors(List<RoleProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public RoleQuery createRoleQuery() {
        return new DefaultRoleQuery();
    }

    @Override
    public Role createRole(String roleId) {
        return this.roleRepository.create(roleId);
    }

    @Transactional
    @Override
    public Role addRole(Role role) {
        return Function.<Role>identity()
                .compose(this.roleRepository::save)
                .compose(this::invokePreProcessBeforeAddRole)
                .apply(role);
    }

    private Role copyFrom(Role source, Role target) {

        return target;
    }

    @Transactional
    @Override
    public Role updateRole(Role role) {
        return Function.<Role>identity()
                .compose(this.roleRepository::save)
                .compose(this::invokePreProcessAfterUpdateRole)
                .<Role>compose(target -> this.copyFrom(role, target))
                .compose(this::invokePreProcessBeforeUpdateRole)
                .compose(this::requiredRole)
                .apply(role.getId());
    }

    private Role requiredRole(String roleId) {
        return this.getRole(roleId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteRole(String roleId) {
        var role = Function.<Role>identity()
                .compose(this::invokePreProcessBeforeDeleteRole)
                .compose(this::requiredRole)
                .apply(roleId);
        this.roleRepository.delete(role);
    }

    @Override
    public Optional<Role> getRole(String roleId) {
        return this.roleRepository.findById(roleId);
    }

    @Override
    public List<Role> getRoles(Set<String> roleIds) {
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
    public Role invokePreProcessBeforeDeleteRole(Role role) {
        return Processors.stream(this.processors)
                .map(RoleProcessor::preProcessBeforeDeleteRole)
                .apply(role);
    }
}
