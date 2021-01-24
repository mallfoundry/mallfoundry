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

package org.mallfoundry.finance;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultTopupQuery extends QuerySupport implements TopupQuery {

    private String accountId;

    private Date createdTimeStart;

    private Date createdTimeEnd;

    private Set<TopupStatus> statuses;

    private Set<PaymentMethodType> sourceTypes;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport extends QueryBuilderSupport<TopupQuery, Builder> implements Builder {

        private final DefaultTopupQuery query;

        public BuilderSupport(DefaultTopupQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder accountId(String accountId) {
            this.query.setAccountId(accountId);
            return this;
        }

        @Override
        public Builder statuses(Set<TopupStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        @Override
        public Builder statuses(Supplier<Set<TopupStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        @Override
        public Builder sourceTypes(Set<PaymentMethodType> sourceTypes) {
            this.query.setSourceTypes(sourceTypes);
            return this;
        }

        @Override
        public Builder sourceTypes(Supplier<Set<PaymentMethodType>> supplier) {
            return this.sourceTypes(supplier.get());
        }

        @Override
        public Builder createdTimeStart(Date createdTimeStart) {
            this.query.setCreatedTimeStart(createdTimeStart);
            return this;
        }

        @Override
        public Builder createdTimeEnd(Date createdTimeEnd) {
            this.query.setCreatedTimeEnd(createdTimeEnd);
            return this;
        }
    }
}

