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

import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.store.StoreQueryBase;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Set;

public interface StaffQuery extends StoreQueryBase, ObjectBuilder.ToBuilder<StaffQuery.Builder> {

    Set<String> getIds();

    void setIds(Set<String> ids);

    Set<StaffStatus> getStatuses();

    void setStatuses(Set<StaffStatus> statuses);

    Set<String> getRoleIds();

    void setRoleIds(Set<String> roleIds);

    interface Builder extends QueryBuilder<StaffQuery, Builder> {

        Builder tenantId(String tenantId);

        Builder storeId(String storeId);

        Builder ids(Set<String> ids);

        Builder roleIds(Set<String> roleIds);

        Builder statuses(Set<StaffStatus> statuses);
    }
}
