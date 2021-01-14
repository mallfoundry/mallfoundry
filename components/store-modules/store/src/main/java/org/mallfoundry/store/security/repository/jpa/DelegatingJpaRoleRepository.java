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

package org.mallfoundry.store.security.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.security.Role;
import org.mallfoundry.store.security.RoleId;
import org.mallfoundry.store.security.RoleQuery;
import org.mallfoundry.store.security.RoleRepository;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DelegatingJpaRoleRepository implements RoleRepository {

    private final JpaRoleRepository repository;

    private final JpaStaffRoleRepository staffRoleRepository;

    public DelegatingJpaRoleRepository(JpaRoleRepository repository, JpaStaffRoleRepository staffRoleRepository) {
        this.repository = repository;
        this.staffRoleRepository = staffRoleRepository;
    }

    @Override
    public Role create(RoleId id) {
        return new JpaRole(id);
    }

    @Override
    public Role save(Role role) {
        return this.repository.save(JpaRole.of(role));
    }

    @Override
    public List<Role> saveAll(List<Role> roles) {
        return CastUtils.cast(
                this.repository.saveAll(
                        roles.stream().map(JpaRole::of).collect(Collectors.toUnmodifiableList())));
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return CastUtils.cast(this.repository.findById(id.getId()));
    }

    @Override
    public List<Role> findAllById(Collection<RoleId> ids) {
        var jpaIds = ids.stream().map(RoleId::getId).collect(Collectors.toUnmodifiableList());
        return CastUtils.cast(this.repository.findAllById(jpaIds));
    }

    @Override
    public List<Role> findAllByStoreId(StoreId storeId) {
        return CastUtils.cast(this.repository.findAllByStoreId(storeId.getId()));
    }

    @Override
    public SliceList<Role> findAll(RoleQuery query) {
        return PageList.of(this.repository.findAll(query));
    }

    @Override
    public void delete(Role role) {
        this.staffRoleRepository.deleteAllByRoleId(role.getId());
        this.repository.delete(JpaRole.of(role));
    }

    @Override
    public void deleteAll(List<Role> roles) {
        var ids = roles.stream().map(Role::getId).collect(Collectors.toUnmodifiableSet());
        this.staffRoleRepository.deleteAllByRoleIdIn(ids);
        this.repository.deleteAll(CastUtils.cast(roles));
    }
}
