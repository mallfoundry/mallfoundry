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
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Set;

@Getter
@Setter
public class DefaultStaffQuery extends QuerySupport implements StaffQuery {

    private String storeId;

    private Set<String> roleIds;

    @Override
    public Builder toBuilder() {
        return new DefaultBuilder(this);
    }

    public static class DefaultBuilder extends QueryBuilderSupport<StaffQuery, Builder> implements Builder {
        private final DefaultStaffQuery query;

        public DefaultBuilder(DefaultStaffQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder roleIds(Set<String> roleIds) {
            this.query.setRoleIds(roleIds);
            return this;
        }
    }
}
