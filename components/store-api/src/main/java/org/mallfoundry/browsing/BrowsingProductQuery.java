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

package org.mallfoundry.browsing;

import org.mallfoundry.data.Pageable;
import org.mallfoundry.data.PageableBuilder;

import java.util.Date;

public interface BrowsingProductQuery extends Pageable {

    String getBrowserId();

    void setBrowserId(String browserId);

    Date getBrowsingTime();

    void setBrowsingTime(Date browsingTime);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends PageableBuilder<BrowsingProductQuery, Builder> {

        Builder browserId(String browserId);

        Builder browsingTime(Date browsingTime);
    }

    abstract class BuilderSupport implements Builder {

        protected final BrowsingProductQuery query;

        public BuilderSupport(BrowsingProductQuery query) {
            this.query = query;
        }

        @Override
        public Builder browserId(String browserId) {
            this.query.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder browsingTime(Date browsingTime) {
            this.query.setBrowsingTime(browsingTime);
            return this;
        }

        @Override
        public Builder page(Integer page) {
            this.query.setPage(page);
            return this;
        }

        @Override
        public Builder limit(Integer limit) {
            this.query.setLimit(limit);
            return this;
        }

        @Override
        public BrowsingProductQuery build() {
            return this.query;
        }
    }
}
