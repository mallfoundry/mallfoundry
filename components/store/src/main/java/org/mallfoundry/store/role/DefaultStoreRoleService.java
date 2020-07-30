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

import org.mallfoundry.data.SliceList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultStoreRoleService implements StoreRoleService {

    private final StoreRoleRepository roleRepository;

    public DefaultStoreRoleService(StoreRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public StoreRoleQuery createRoleQuery() {
        return new DefaultStoreRoleQuery();
    }

    @Override
    public StoreRole createRole(String roleId) {
        return this.roleRepository.create(roleId);
    }

    @Transactional
    @Override
    public StoreRole addRole(StoreRole role) {
        return this.roleRepository.save(role);
    }

    @Transactional
    @Override
    public StoreRole updateRole(StoreRole role) {
        return this.roleRepository.save(role);
    }

    private StoreRole requiredRole(String roleId) {
        return this.getRole(roleId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteRole(String roleId) {
        var role = this.requiredRole(roleId);
        this.roleRepository.delete(role);
    }

    @Override
    public Optional<StoreRole> getRole(String roleId) {
        return this.roleRepository.findById(roleId);
    }

    @Override
    public List<StoreRole> getRoles(Set<String> roleIds) {
        return this.roleRepository.findAllById(roleIds);
    }

    @Override
    public SliceList<StoreRole> getRoles(StoreRoleQuery query) {
        return this.roleRepository.findAll(query);
    }
}
