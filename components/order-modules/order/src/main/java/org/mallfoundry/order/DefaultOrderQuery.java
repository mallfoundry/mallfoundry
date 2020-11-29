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

package org.mallfoundry.order;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.data.QueryBuilderSupport;
import org.mallfoundry.data.QuerySupport;
import org.mallfoundry.finance.PaymentMethod;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class DefaultOrderQuery extends QuerySupport implements OrderQuery {
    private Set<String> ids;
    private String tenantId;
    private String storeId;
    private String customerId;
    private String name;
    private Set<OrderStatus> statuses;
    private Set<OrderStatus> disputeStatuses;
    private Set<OrderStatus> reviewStatuses;
    private Set<OrderType> types;
    private Set<PaymentMethod> paymentMethods;
    private Set<OrderSource> sources;
    private Date placedTimeMin;
    private Date placedTimeMax;
    private Date placingExpiredTimeMin;
    private Date placingExpiredTimeMax;

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this);
    }

    public static class BuilderSupport extends QueryBuilderSupport<OrderQuery, Builder> implements Builder {

        private final OrderQuery query;

        public BuilderSupport(OrderQuery query) {
            super(query);
            this.query = query;
        }

        @Override
        public Builder ids(Set<String> ids) {
            this.query.setIds(ids);
            return this;
        }

        public Builder name(String name) {
            this.query.setName(name);
            return this;
        }

        public Builder customerId(String customerId) {
            this.query.setCustomerId(customerId);
            return this;
        }

        public Builder storeId(String storeId) {
            this.query.setStoreId(storeId);
            return this;
        }

        public Builder statuses(Supplier<Set<OrderStatus>> supplier) {
            return this.statuses(supplier.get());
        }

        public Builder statuses(Set<OrderStatus> statuses) {
            this.query.setStatuses(statuses);
            return this;
        }

        @Override
        public Builder disputeStatuses(Supplier<Set<OrderStatus>> supplier) {
            return this.disputeStatuses(supplier.get());
        }

        @Override
        public Builder disputeStatuses(Set<OrderStatus> statuses) {
            this.query.setDisputeStatuses(statuses);
            return this;
        }

        @Override
        public Builder reviewStatuses(Supplier<Set<OrderStatus>> supplier) {
            return this.reviewStatuses(supplier.get());
        }

        @Override
        public Builder reviewStatuses(Set<OrderStatus> statuses) {
            this.query.setReviewStatuses(statuses);
            return this;
        }

        @Override
        public Builder sources(Set<OrderSource> sources) {
            this.query.setSources(sources);
            return this;
        }

        @Override
        public Builder sources(Supplier<Set<OrderSource>> supplier) {
            return this.sources(supplier.get());
        }

        @Override
        public Builder types(Set<OrderType> types) {
            this.query.setTypes(types);
            return this;
        }

        @Override
        public Builder types(Supplier<Set<OrderType>> supplier) {
            return this.types(supplier.get());
        }

        @Override
        public Builder paymentMethods(Set<PaymentMethod> methods) {
            this.query.setPaymentMethods(methods);
            return this;
        }

        @Override
        public Builder paymentMethods(Supplier<Set<PaymentMethod>> supplier) {
            return this.paymentMethods(supplier.get());
        }

        @Override
        public Builder placedTimeMin(Date time) {
            this.query.setPlacedTimeMin(time);
            return this;
        }

        @Override
        public Builder placedTimeMax(Date time) {
            this.query.setPlacedTimeMax(time);
            return this;
        }

        @Override
        public Builder placingExpiredTimeMin(Date time) {
            this.query.setPlacingExpiredTimeMin(time);
            return this;
        }

        @Override
        public Builder placingExpiredTimeMax(Date time) {
            this.query.setPlacingExpiredTimeMax(time);
            return this;
        }

        public OrderQuery build() {
            return this.query;
        }
    }
}
