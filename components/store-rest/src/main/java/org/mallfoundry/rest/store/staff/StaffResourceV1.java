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

package org.mallfoundry.rest.store.staff;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.security.RoleService;
import org.mallfoundry.store.staff.Staff;
import org.mallfoundry.store.staff.StaffService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class StaffResourceV1 {

    private final StaffService staffService;

    private final RoleService roleService;

    public StaffResourceV1(StaffService staffService, RoleService roleService) {
        this.staffService = staffService;
        this.roleService = roleService;
    }

    private Staff assignToStaff(StaffRequest request, Staff staff) {
        var storeId = staff.getStoreId();
        staff = request.assignTo(staff);
        var roles = request.getRoles().stream()
                .map(role -> this.roleService.createRoleId(storeId, role.getId()))
                .map(this.roleService::createRole)
                .collect(Collectors.toList());
        staff.setRoles(roles);
        return staff;
    }

    @PostMapping("/stores/{store_id}/staffs")
    public Staff addStaff(@PathVariable("store_id") String storeId,
                          @RequestBody StaffRequest request) {
        return Function.<Staff>identity()
                .compose(this.staffService::addStaff)
                .<Staff>compose(staff -> this.assignToStaff(request, staff))
                .compose(this.staffService::createStaff)
                .apply(this.staffService.createStaffId(storeId, request.getId()));
    }

    @PatchMapping("/stores/{store_id}/staffs/{staff_id}")
    public Staff updateStaff(@PathVariable("store_id") String storeId,
                             @PathVariable("staff_id") String staffId,
                             @RequestBody StaffRequest request) {
        return Function.<Staff>identity()
                .compose(this.staffService::updateStaff)
                .<Staff>compose(staff -> this.assignToStaff(request, staff))
                .compose(this.staffService::createStaff)
                .apply(this.staffService.createStaffId(storeId, staffId));
    }

    @GetMapping("/stores/{store_id}/staffs/{staff_id}")
    public Optional<Staff> getStaff(@PathVariable("store_id") String storeId,
                                    @PathVariable("staff_id") String staffId) {
        return this.staffService.getStaff(this.staffService.createStaffId(storeId, staffId));
    }

    @GetMapping("/stores/{store_id}/staffs")
    public SliceList<Staff> getStaffs(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                      @PathVariable("store_id") String storeId,
                                      @RequestParam(name = "role_ids", required = false) Set<String> roleIds) {
        return this.staffService.getStaffs(
                this.staffService.createStaffQuery()
                        .toBuilder().page(page).limit(limit)
                        .storeId(storeId).roleIds(roleIds).build());
    }


    @GetMapping("/stores/{store_id}/staffs/count")
    public long countStaffs(@RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                            @PathVariable("store_id") String storeId,
                            @RequestParam(name = "role_ids", required = false) Set<String> roleIds) {
        return this.staffService.countStaffs(
                this.staffService.createStaffQuery()
                        .toBuilder().page(page).limit(limit)
                        .storeId(storeId).roleIds(roleIds).build());
    }

    @DeleteMapping("/stores/{store_id}/staffs/{staff_id}")
    public void deleteStaff(@PathVariable("store_id") String storeId,
                            @PathVariable("staff_id") String staffId) {
        this.staffService.deleteStaff(this.staffService.createStaffId(storeId, staffId));
    }
}
