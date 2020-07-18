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

package org.mallfoundry.order.expires;


import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderProcessor;
import org.mallfoundry.order.OrderQuery;
import org.mallfoundry.order.OrderStatus;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用订单处理器来支持取消超时过期订单。
 *
 * @author Zhi Tang
 */
public class OrderExpiredCancellationProcessor implements OrderProcessor {

    private final OrderExpiredCanceller canceller;

    private final ThreadLocal<OrderQuery> queryFor = new ThreadLocal<>();

    private final Set<OrderStatus> conditionStatuses = Set.of(OrderStatus.PENDING, OrderStatus.AWAITING_PAYMENT);

    public OrderExpiredCancellationProcessor(OrderExpiredCanceller canceller) {
        this.canceller = canceller;
    }

    // 判断订单对象是否为必须取消。
    private boolean mustCancel(Order order) {
        return order.isPlacingExpired() && this.conditionStatuses.contains(order.getStatus());
    }

    @Override
    public Order postProcessGetOrder(Order order) {
        if (this.mustCancel(order)) {
            this.canceller.cancelOrders(List.of(order));
        }
        return order;
    }

    @Override
    public OrderQuery preProcessGetOrders(OrderQuery query) {
        queryFor.set(query);
        return query;
    }

    private List<Order> getExpiredOrders(SliceList<Order> orders) {
        return orders.getElements().stream()
                .filter(this::mustCancel)
                .collect(Collectors.toUnmodifiableList());
    }

    // 如果存在过期订单，此流程将会执行两次。
    // 第一次为正常流程，第二次为 canceller.getOrders(query)。
    @Override
    public SliceList<Order> postProcessGetOrders(SliceList<Order> orders) {
        if (CollectionUtils.isEmpty(orders.getElements())) {
            return orders;
        }
        var query = queryFor.get();
        var expiredOrders = this.getExpiredOrders(orders);
        if (CollectionUtils.isNotEmpty(expiredOrders)) {
            this.canceller.cancelOrders(expiredOrders);
            return this.canceller.getOrders(query);
        }
        return orders;
    }

    @Override
    public void postProcessAfterCompletion() {
        queryFor.remove();
    }
}
