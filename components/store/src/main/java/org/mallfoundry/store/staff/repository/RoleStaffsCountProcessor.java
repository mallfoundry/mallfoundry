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

package org.mallfoundry.store.staff.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.store.security.Role;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.store.staff.StaffProcessor;
import org.springframework.core.NamedThreadLocal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RoleStaffsCountProcessor implements StaffProcessor {

    private final ThreadLocal<List<Role>> localRoles = new NamedThreadLocal<>("RoleStaffsCount");

    private final RoleService roleService;

    public RoleStaffsCountProcessor(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Staff preProcessAfterAddStaff(Staff staff) {
        this.addStaffToRoles(staff, staff.getRoles());
        return staff;
    }

    @Override
    public Staff preProcessBeforeUpdateStaff(Staff staff) {
        localRoles.set(Collections.unmodifiableList(staff.getRoles()));
        return staff;
    }

    @Override
    public Staff preProcessAfterUpdateStaff(Staff staff) {
        var removedRoles = CollectionUtils.subtract(localRoles.get(), staff.getRoles());
        var newRoles = CollectionUtils.subtract(staff.getRoles(), localRoles.get());
        this.addStaffToRoles(staff, newRoles);
        this.removeStaffFromRoles(staff, removedRoles);
        return staff;
    }

    @Override
    public void preProcessAfterCompletion() {
        this.localRoles.remove();
    }

    @Override
    public Staff preProcessAfterDeleteStaff(Staff staff) {
        this.removeStaffFromRoles(staff, staff.getRoles());
        return staff;
    }

    private void addStaffToRoles(Staff staff, Collection<Role> roles) {
        for (var role : roles) {
            var roleId = this.roleService.createRoleId(role.getStoreId(), role.getId());
            this.roleService.addRoleStaff(roleId, staff);
        }
    }

    private void removeStaffFromRoles(Staff staff, Collection<Role> roles) {
        for (var role : roles) {
            var roleId = this.roleService.createRoleId(role.getStoreId(), role.getId());
            this.roleService.removeRoleStaff(roleId, staff);
        }
    }
}
