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
import org.mallfoundry.store.role.StoreRole;
import org.mallfoundry.store.role.StoreRoleQuery;
import org.mallfoundry.store.role.StoreRoleRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class JpaStoreRoleRepository implements StoreRoleRepository {

    private final JpaStoreRoleRepositoryDelegate repository;

    public JpaStoreRoleRepository(JpaStoreRoleRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public StoreRole create(String id) {
        return new JpaStoreRole(id);
    }

    @Override
    public StoreRole save(StoreRole role) {
        return this.repository.save(JpaStoreRole.of(role));
    }

    @Override
    public Optional<StoreRole> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public SliceList<StoreRole> findAll(StoreRoleQuery query) {
        var page = this.repository.findAll(query);
        var list = PageList.of(page.getContent()).page(page.getNumber()).limit(query.getLimit()).totalSize(page.getTotalElements());
        return CastUtils.cast(list);
    }

    @Override
    public void delete(StoreRole role) {
        this.repository.delete(JpaStoreRole.of(role));
    }
}
