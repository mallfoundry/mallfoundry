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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.mallfoundry.store.security.Role;
import org.mallfoundry.store.security.RoleService;
import org.springframework.core.NamedThreadLocal;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoleStaffsCountProcessor implements StaffProcessor {

    private final ThreadLocal<List<Role>> localRoles = new NamedThreadLocal<>("RoleStaffsCount");

    private final RoleService roleService;

    public RoleStaffsCountProcessor(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Staff preProcessBeforeUpdateStaff(Staff staff) {
        localRoles.set(Collections.unmodifiableList(staff.getRoles()));
        return staff;
    }

    @Override
    public Staff preProcessAfterUpdateStaff(Staff staff) {
        var removedRoles = CollectionUtils.subtract(localRoles.get(), staff.getRoles());
        var addRoles = CollectionUtils.subtract(staff.getRoles(), localRoles.get());
        var roleIds = Stream.concat(removedRoles.stream(), addRoles.stream())
                .map(role -> this.roleService.createRoleId(role.getStoreId(), role.getId()))
                .collect(Collectors.toUnmodifiableSet());
        var allRoles = this.roleService.getRoles(roleIds);
        for (var role : removedRoles) {
            IterableUtils.find(allRoles, aRole -> aRole.equals(role)).removeStaff(staff);
        }
        for (var role : addRoles) {
            IterableUtils.find(allRoles, aRole -> aRole.equals(role)).addStaff(staff);
        }
        this.roleService.updateRoles(allRoles);
        return staff;
    }

    @Override
    public void preProcessAfterCompletion() {
        this.localRoles.remove();
    }
}
