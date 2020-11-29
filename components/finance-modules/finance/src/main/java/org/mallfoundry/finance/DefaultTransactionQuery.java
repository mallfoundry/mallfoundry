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

import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultTransactionQuery extends QuerySupport implements TransactionQuery {

    private String accountId;

    private Set<TransactionStatus> statuses;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport extends QueryBuilderSupport<TransactionQuery, Builder> implements Builder {

        private final DefaultTransactionQuery query;

        protected BuilderSupport(DefaultTransactionQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder accountId(String accountId) {
            this.query.setAccountId(accountId);
            return this;
        }

        @Override
        public Builder statuses(Set<TransactionStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        @Override
        public Builder statuses(Supplier<Set<TransactionStatus>> supplier) {
            return this.statuses(supplier.get());
        }
    }
}
