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

package org.mallfoundry.store.staff;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.role.StoreRoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public class DefaultStaffService implements StaffService {

    private final StaffRepository staffRepository;

    private final StoreRoleService storeRoleService;

    public DefaultStaffService(StaffRepository staffRepository, StoreRoleService storeRoleService) {
        this.staffRepository = staffRepository;
        this.storeRoleService = storeRoleService;
    }

    @Override
    public StaffQuery createStaffQuery() {
        return new DefaultStaffQuery();
    }

    @Override
    public Staff createStaff(String staffId) {
        return this.staffRepository.create(staffId);
    }

    @Transactional
    @Override
    public Staff addStaff(Staff staff) {
        return this.staffRepository.save(staff);
    }

    @Transactional
    @Override
    public Staff updateStaff(Staff staff) {
        return this.staffRepository.save(staff);
    }

    private Staff requiredStaff(String staffId) {
        return this.getStaff(staffId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteStaff(String staffId) {
        var staff = this.requiredStaff(staffId);
        this.staffRepository.delete(staff);
    }

    @Override
    public Optional<Staff> getStaff(String staffId) {
        return this.staffRepository.findById(staffId);
    }

    @Override
    public SliceList<Staff> getStaffs(StaffQuery query) {
        return this.staffRepository.findAll(query);
    }

    @Transactional
    @Override
    public void addStaffRole(String staffId, String roleId) {
        this.addStaffRoles(staffId, Set.of(roleId));
    }

    @Transactional
    @Override
    public void addStaffRoles(String staffId, Set<String> roleIds) {
        var staff = this.requiredStaff(staffId);
        var roles = this.storeRoleService.getRoles(roleIds);
        staff.addRoles(roles);
    }

    @Transactional
    @Override
    public void removeStaffRole(String staffId, String roleId) {
        this.removeStaffRoles(staffId, Set.of(roleId));
    }

    @Transactional
    @Override
    public void removeStaffRoles(String staffId, Set<String> roleIds) {
        var staff = this.requiredStaff(staffId);
        var roles = this.storeRoleService.getRoles(roleIds);
        staff.removeRoles(roles);
    }
}
