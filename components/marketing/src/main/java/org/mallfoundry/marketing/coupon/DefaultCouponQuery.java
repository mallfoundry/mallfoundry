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

package org.mallfoundry.marketing.coupon;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;

import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultCouponQuery extends QuerySupport implements CouponQuery {
    private String tenantId;
    private String storeId;
    private String customerId;
    private String name;
    private Set<CouponType> types;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport
            extends QueryBuilderSupport<CouponQuery, Builder> implements Builder {

        private final DefaultCouponQuery query;

        public BuilderSupport(DefaultCouponQuery query) {
            super(query);
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
        public Builder customerId(String customerId) {
            this.query.setCustomerId(customerId);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.query.setName(name);
            return this;
        }

        @Override
        public Builder types(Set<CouponType> types) {
            this.query.setTypes(types);
            return this;
        }

        @Override
        public Builder types(Supplier<Set<CouponType>> supplier) {
            return this.types(supplier.get());
        }
    }
}
