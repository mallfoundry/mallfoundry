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

package org.mallfoundry.store.security;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ImmutableRoleId implements RoleId {
    private final String tenantId;
    private final String storeId;
    private final String id;

    public ImmutableRoleId(String tenantId, String storeId, String id) {
        this.tenantId = tenantId;
        this.storeId = storeId;
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImmutableRoleId)) {
            return false;
        }
        ImmutableRoleId that = (ImmutableRoleId) object;
        return Objects.equals(tenantId, that.tenantId)
                && Objects.equals(storeId, that.storeId)
                && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, storeId, id);
    }
}
