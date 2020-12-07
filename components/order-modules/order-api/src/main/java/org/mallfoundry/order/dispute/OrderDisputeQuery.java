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

package org.mallfoundry.order.dispute;

import org.mallfoundry.customer.CustomerOwnership;
import org.mallfoundry.data.Query;
import org.mallfoundry.data.QueryBuilder;
import org.mallfoundry.store.StoreOwnership;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.Set;
import java.util.function.Supplier;

public interface OrderDisputeQuery extends Query, StoreOwnership, CustomerOwnership, ObjectBuilder.ToBuilder<OrderDisputeQuery.Builder> {

    String getOrderId();

    void setOrderId(String orderId);

    Set<String> getIds();

    void setIds(Set<String> ids);

    Date getAppliedTimeMin();

    void setAppliedTimeMin(Date appliedTimeMin);

    Date getAppliedTimeMax();

    void setAppliedTimeMax(Date appliedTimeMax);

    Set<OrderDisputeKind> getKinds();

    void setKinds(Set<OrderDisputeKind> kinds);

    Set<OrderDisputeStatus> getStatuses();

    void setStatuses(Set<OrderDisputeStatus> statuses);

    interface Builder extends QueryBuilder<OrderDisputeQuery, Builder> {

        Builder tenantId(String tenantId);

        Builder storeId(String storeId);

        Builder customerId(String customerId);

        Builder orderId(String orderId);

        Builder ids(Set<String> ids);

        Builder kinds(Supplier<Set<OrderDisputeKind>> supplier);

        Builder kinds(Set<OrderDisputeKind> kinds);

        Builder statuses(Supplier<Set<OrderDisputeStatus>> supplier);

        Builder statuses(Set<OrderDisputeStatus> statuses);
    }
}
