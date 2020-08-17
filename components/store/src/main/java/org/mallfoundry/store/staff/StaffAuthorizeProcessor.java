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

import org.mallfoundry.security.access.AllAuthorities;
import org.mallfoundry.security.access.Resource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 店铺职员对象鉴权者。
 *
 * @author Zhi Tang
 */
public class StaffAuthorizeProcessor implements StaffProcessor {

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_ADD + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeAddStaff(Staff staff) {
        return staff;
    }

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_READ + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff postProcessAfterGetStaff(Staff staff) {
        return staff;
    }

    @PreAuthorize("hasPermission(#query.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_READ + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public StaffQuery preProcessBeforeGetStaffs(StaffQuery query) {
        return query;
    }

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_WRITE + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeUpdateStaff(Staff staff) {
        return staff;
    }

    @PreAuthorize("hasPermission(#staff.storeId, '" + Resource.STORE_TYPE + "', '"
            + AllAuthorities.STORE_STAFF_DELETE + ","
            + AllAuthorities.STORE_STAFF_MANAGE + ","
            + AllAuthorities.STORE_MANAGE + "')")
    @Override
    public Staff preProcessBeforeDeleteStaff(Staff staff) {
        return staff;
    }
}
