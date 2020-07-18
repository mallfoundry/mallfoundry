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

import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderStatus;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 使用定时任务来去取消超时过期订单。
 *
 * @author Zhi Tang
 */
public class OrderExpiredCancellationTask implements Runnable {

    public static final int DEFAULT_FETCH_SIZE = 30;

    private int fetchSize = DEFAULT_FETCH_SIZE;

    private final OrderExpiredCanceller canceller;

    public OrderExpiredCancellationTask(OrderExpiredCanceller canceller) {
        this.canceller = canceller;
    }

    public void setFetchSize(int fetchSize) {
        Assert.state(fetchSize > 0, "Property 'fetchSize' cannot be less than zero");
        this.fetchSize = fetchSize;
    }

    @Override
    public void run() {
        this.canceller.cancelOrders(this.getExpiredOrders());
    }

    private List<Order> getExpiredOrders() {
        var sliceList = this.canceller.getOrders(this.canceller.createOrderQuery()
                .toBuilder()
                .statuses(Set.of(OrderStatus.PENDING, OrderStatus.AWAITING_PAYMENT))
                .placingExpiredTimeMax(new Date())
                .limit(this.fetchSize).build());
        return sliceList.getElements();
    }
}
