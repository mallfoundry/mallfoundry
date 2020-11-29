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

package org.mallfoundry.page;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultPageViewQuery extends QuerySupport implements PageViewQuery {

    private String pageId;

    private Set<PageType> pageTypes;

    private String browserId;

    private String browserIp;

    private Date browsingTimeFrom;

    private Date browsingTimeTo;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport extends QueryBuilderSupport<PageViewQuery, Builder> implements Builder {
        private final DefaultPageViewQuery query;

        public BuilderSupport(DefaultPageViewQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder pageId(String pageId) {
            this.query.setPageId(pageId);
            return this;
        }

        @Override
        public Builder pageTypes(Set<PageType> pageTypes) {
            this.query.setPageTypes(pageTypes);
            return this;
        }

        @Override
        public Builder pageTypes(Supplier<Set<PageType>> supplier) {
            return this.pageTypes(supplier.get());
        }

        @Override
        public Builder browserId(String browserId) {
            this.query.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder browserIp(String browserIp) {
            this.query.setBrowserIp(browserIp);
            return this;
        }

        @Override
        public Builder browsingTimeFrom(Date browsingTimeFrom) {
            this.query.setBrowsingTimeFrom(browsingTimeFrom);
            return this;
        }

        @Override
        public Builder browsingTimeTo(Date browsingTimeTo) {
            this.query.setBrowsingTimeTo(browsingTimeTo);
            return this;
        }
    }
}
