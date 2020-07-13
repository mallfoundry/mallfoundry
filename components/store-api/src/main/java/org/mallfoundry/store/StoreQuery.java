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

package org.mallfoundry.store;

import org.mallfoundry.data.Query;

public interface StoreQuery extends Query {

    String getOwnerId();

    void setOwnerId(String ownerId);

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final StoreQuery query;

        public Builder(StoreQuery query) {
            this.query = query;
        }

        public Builder page(Integer page) {
            this.query.setPage(page);
            return this;
        }

        public Builder limit(Integer limit) {
            this.query.setLimit(limit);
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.query.setOwnerId(ownerId);
            return this;
        }

        public StoreQuery build() {
            return this.query;
        }
    }
}
