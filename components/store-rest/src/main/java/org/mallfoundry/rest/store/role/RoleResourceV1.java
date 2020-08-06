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

package org.mallfoundry.rest.store.role;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.role.Role;
import org.mallfoundry.store.role.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class RoleResourceV1 {
    private final RoleService roleService;

    public RoleResourceV1(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/stores/{store_id}/roles")
    public Role addRole(@PathVariable("store_id") String storeId,
                        @RequestBody RoleRequest request) {
        var roleId = this.roleService.createRoleId(storeId, null);
        var store = this.roleService.createRole(roleId);
        return this.roleService.addRole(request.assignTo(store));
    }

    @PostMapping("/stores/{store_id}/roles/{role_id}")
    public Role updateRole(@PathVariable("store_id") String storeId,
                           @PathVariable("role_id") String roleId,
                           @RequestBody RoleRequest request) {
        var aRoleId = this.roleService.createRoleId(storeId, roleId);
        var store = this.roleService.createRole(aRoleId);
        return this.roleService.updateRole(request.assignTo(store));
    }

    @GetMapping("/stores/{store_id}/roles/{role_id}")
    public Optional<Role> getRoles(@PathVariable("store_id") String storeId,
                                   @PathVariable("role_id") String roleId) {
        return this.roleService.getRole(this.roleService.createRoleId(storeId, roleId));
    }

    @GetMapping("/stores/{store_id}/roles")
    public SliceList<Role> getRoles(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                    @PathVariable("store_id") String storeId) {
        return this.roleService.getRoles(
                this.roleService.createRoleQuery().toBuilder()
                        .page(page).limit(limit).storeId(storeId).build());
    }
}
