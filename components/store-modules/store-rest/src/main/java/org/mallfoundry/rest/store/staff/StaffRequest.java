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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.store.staff.Staff;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StaffRequest {
    private String id;
    private String name;
    private String number;
    private String countryCode;
    private String phone;
    private List<RoleRequest> roles = new ArrayList<>();

    public Staff assignTo(Staff staff) {
        return staff.toBuilder()
                .id(this.id).name(this.name).number(number)
                .countryCode(this.countryCode).phone(this.phone)
                .build();
    }

    @Getter
    @Setter
    static class RoleRequest {
        private String id;
    }
}
