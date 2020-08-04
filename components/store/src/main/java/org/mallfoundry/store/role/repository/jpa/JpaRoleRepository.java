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

package org.mallfoundry.store.role.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.role.Role;
import org.mallfoundry.store.role.RoleQuery;
import org.mallfoundry.store.role.RoleRepository;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class JpaRoleRepository implements RoleRepository {

    private final JpaRoleRepositoryDelegate repository;

    public JpaRoleRepository(JpaRoleRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public Role create(String id) {
        return new JpaRole(id);
    }

    @Override
    public Role save(Role role) {
        return this.repository.save(JpaRole.of(role));
    }

    @Override
    public Optional<Role> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public List<Role> findAllById(Collection<String> ids) {
        return CastUtils.cast(this.repository.findAllById(ids));
    }

    @Override
    public SliceList<Role> findAll(RoleQuery query) {
        var page = this.repository.findAll(query);
        var list = PageList.of(page.getContent()).page(page.getNumber()).limit(query.getLimit()).totalSize(page.getTotalElements());
        return CastUtils.cast(list);
    }

    @Override
    public void delete(Role role) {
        this.repository.delete(JpaRole.of(role));
    }
}