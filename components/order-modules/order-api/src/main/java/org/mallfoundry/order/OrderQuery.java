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

import org.mallfoundry.customer.CustomerOwnership;
import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.finance.PaymentMethod;
import org.mallfoundry.store.StoreOwnership;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;


public interface OrderQuery extends Query, StoreOwnership, CustomerOwnership {

    Set<String> getIds();

    void setIds(Set<String> ids);

    String getName();

    void setName(String name);

    Set<OrderStatus> getStatuses();

    void setStatuses(Set<OrderStatus> statuses);

    Set<OrderStatus> getDisputeStatuses();

    void setDisputeStatuses(Set<OrderStatus> statuses);

    Set<OrderStatus> getReviewStatuses();

    void setReviewStatuses(Set<OrderStatus> statuses);

    Set<OrderType> getTypes();

    void setTypes(Set<OrderType> types);

    Set<PaymentMethod> getPaymentMethods();

    void setPaymentMethods(Set<PaymentMethod> methods);

    Set<OrderSource> getSources();

    void setSources(Set<OrderSource> sources);

    Date getPlacedTimeMin();

    void setPlacedTimeMin(Date time);

    Date getPlacedTimeMax();

    void setPlacedTimeMax(Date time);

    Date getPlacingExpiredTimeMin();

    void setPlacingExpiredTimeMin(Date time);

    Date getPlacingExpiredTimeMax();

    void setPlacingExpiredTimeMax(Date time);

    Builder toBuilder();

    interface Builder extends QueryBuilder<OrderQuery, Builder> {

        Builder ids(Set<String> ids);

        Builder name(String name);

        Builder customerId(String customerId);

        Builder storeId(String storeId);

        Builder statuses(Supplier<Set<OrderStatus>> supplier);

        Builder statuses(Set<OrderStatus> statuses);

        Builder disputeStatuses(Supplier<Set<OrderStatus>> supplier);

        Builder disputeStatuses(Set<OrderStatus> statuses);

        Builder reviewStatuses(Supplier<Set<OrderStatus>> supplier);

        Builder reviewStatuses(Set<OrderStatus> statuses);

        Builder sources(Set<OrderSource> sources);

        Builder sources(Supplier<Set<OrderSource>> supplier);

        Builder types(Set<OrderType> types);

        Builder types(Supplier<Set<OrderType>> supplier);

        Builder paymentMethods(Set<PaymentMethod> methods);

        Builder paymentMethods(Supplier<Set<PaymentMethod>> supplier);

        Builder placedTimeMin(Date time);

        Builder placedTimeMax(Date time);

        Builder placingExpiredTimeMin(Date time);

        Builder placingExpiredTimeMax(Date time);
    }
}
