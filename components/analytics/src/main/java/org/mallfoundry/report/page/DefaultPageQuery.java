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

package org.mallfoundry.report.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultPageQuery implements PageQuery {

    private String tenantId;

    private String storeId;

    private String browserId;

    private Integer date;

    private Integer dateFrom;

    private Integer dateTo;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final DefaultPageQuery query;

        protected BuilderSupport(DefaultPageQuery query) {
            this.query = query;
        }

        @Override
        public Builder tenantId(String tenantId) {
            this.query.setTenantId(tenantId);
            return this;
        }

        @Override
        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder browserId(String browserId) {
            this.query.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder date(Integer date) {
            this.query.setDate(date);
            return this;
        }

        @Override
        public Builder dateFrom(Integer dateFrom) {
            this.query.setDateFrom(dateFrom);
            return this;
        }

        @Override
        public Builder dateTo(Integer dateTo) {
            this.query.setDateTo(dateTo);
            return this;
        }

        @Override
        public PageQuery build() {
            return this.query;
        }
    }
}
