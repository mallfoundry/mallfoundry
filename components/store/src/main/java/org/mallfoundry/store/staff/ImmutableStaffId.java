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

import lombok.Getter;

import java.util.Objects;

@Getter
public class ImmutableStaffId implements StaffId {
    private final String storeId;
    private final String staffId;

    public ImmutableStaffId(String storeId, String staffId) {
        this.storeId = storeId;
        this.staffId = staffId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImmutableStaffId)) {
            return false;
        }
        ImmutableStaffId that = (ImmutableStaffId) object;
        return Objects.equals(this.storeId, that.storeId) && Objects.equals(this.staffId, that.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.storeId, this.staffId);
    }
}
