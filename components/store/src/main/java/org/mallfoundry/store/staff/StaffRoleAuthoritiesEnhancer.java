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

import org.mallfoundry.identity.UserId;
import org.mallfoundry.security.UserAuthoritiesEnhancer;
import org.mallfoundry.store.security.Roles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

public class StaffRoleAuthoritiesEnhancer implements UserAuthoritiesEnhancer {

    private final StaffService staffService;

    public StaffRoleAuthoritiesEnhancer(StaffService staffService) {
        this.staffService = staffService;
    }

    @Transactional
    @Override
    public Collection<String> getAuthorities(UserId userId) {
        var staffs = this.staffService.getActiveStaffs(userId);
        return staffs.stream().map(Staff::getRoles)
                .flatMap(Collection::stream)
                .map(Roles::getRoleAuthority)
                .collect(Collectors.toUnmodifiableSet());
    }
}
