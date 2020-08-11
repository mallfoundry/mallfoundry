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

import org.mallfoundry.store.security.Role;
import org.mallfoundry.store.security.RoleProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class StaffRoleRemovalProcessor implements RoleProcessor {

    private final StaffRoleRepository repository;

    public StaffRoleRemovalProcessor(StaffRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role preProcessAfterDeleteRole(Role role) {
        this.repository.deleteAllByRoleId(role.toId());
        return role;
    }

    @Override
    public List<Role> preProcessAfterClearRoles(List<Role> roles) {
        var roleIds = roles.stream().map(Role::toId).collect(Collectors.toUnmodifiableSet());
        this.repository.deleteAllByRoleIds(roleIds);
        return roles;
    }
}
