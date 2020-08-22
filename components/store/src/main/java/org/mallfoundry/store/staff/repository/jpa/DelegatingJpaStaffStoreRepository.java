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
import org.mallfoundry.store.staff.StaffStore;
import org.mallfoundry.store.staff.StaffStoreQuery;
import org.mallfoundry.store.staff.StaffStoreRepository;

import java.util.HashMap;
import java.util.HashSet;

public class DelegatingJpaStaffStoreRepository implements StaffStoreRepository {

    private final JpaOnlyStaffRepository staffRepository;

    private final JpaStaffStoreRepository repository;

    public DelegatingJpaStaffStoreRepository(JpaOnlyStaffRepository staffRepository,
                                             JpaStaffStoreRepository repository) {
        this.staffRepository = staffRepository;
        this.repository = repository;
    }

    @Override
    public SliceList<StaffStore> findAll(StaffStoreQuery query) {
        var pageStaffs = this.staffRepository.findAll(query);
        var storeStaffs = new HashMap<String, JpaOnlyStaff>();
        var storeIds = new HashSet<String>();
        pageStaffs.forEach(staff -> {
            storeStaffs.put(staff.getStoreId(), staff);
            storeIds.add(staff.getStoreId());
        });
        var stores = this.repository.findAllByIdInOrderByCreatedTimeAsc(storeIds);
        stores.forEach(store -> store.setStaff(storeStaffs.get(store.getId())));
        return PageList.of(stores)
                .page(pageStaffs.getNumber()).limit(query.getLimit())
                .totalSize(pageStaffs.getTotalElements())
                .cast();
    }
}
