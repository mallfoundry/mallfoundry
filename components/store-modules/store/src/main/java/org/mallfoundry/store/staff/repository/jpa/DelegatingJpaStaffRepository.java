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

package org.mallfoundry.store.staff.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.store.staff.StaffId;
import org.mallfoundry.store.staff.StaffQuery;
import org.mallfoundry.store.staff.StaffRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaStaffRepository implements StaffRepository {

    private final JpaStaffRepository repository;

    public DelegatingJpaStaffRepository(JpaStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public Staff create(StaffId id) {
        return new JpaStaff(id);
    }

    @Override
    public Staff save(Staff staff) {
        return this.repository.save(JpaStaff.of(staff));
    }

    @Override
    public Optional<Staff> findById(StaffId id) {
        return CastUtils.cast(this.repository.findById(new JpaStaffId(id.getStoreId(), id.getId())));
    }

    @Override
    public SliceList<Staff> findAll(StaffQuery query) {
        var page = this.repository.findAll(query);
        return PageList.of(page.getContent())
                .page(query.getPage()).limit(query.getLimit())
                .totalSize(page.getTotalElements())
                .cast();
    }

    @Override
    public long count(StaffQuery query) {
        return this.repository.count(query);
    }

    @Override
    public void delete(Staff staff) {
        this.repository.delete(JpaStaff.of(staff));
    }

    @Override
    public void deleteAllByStoreId(StoreId storeId) {
        this.repository.deleteAllByStoreId(storeId.getId());
    }
}
