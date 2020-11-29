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
import org.mallfoundry.store.staff.StaffStore;
import org.mallfoundry.store.staff.StaffStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class StaffStoreResourceV1 {

    private final StaffStoreService staffStoreService;

    public StaffStoreResourceV1(StaffStoreService staffStoreService) {
        this.staffStoreService = staffStoreService;
    }

    @GetMapping("/staffs/stores")
    public SliceList<StaffStore> getStaffStores(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                @RequestParam(value = "staff_ids") Set<String> staffIds) {
        return this.staffStoreService.getStaffStores(
                this.staffStoreService.createStaffStoreQuery()
                        .toBuilder().page(page).limit(limit).staffIds(staffIds).build());
    }

}
