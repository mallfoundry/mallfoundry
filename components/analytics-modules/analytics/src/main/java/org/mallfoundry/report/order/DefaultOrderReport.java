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

package org.mallfoundry.report.order;

import java.util.List;

public class DefaultOrderReport implements OrderReport {

    private final OrderReportRepository orderReportRepository;

    public DefaultOrderReport(OrderReportRepository orderReportRepository) {
        this.orderReportRepository = orderReportRepository;
    }

    @Override
    public OrderQuery createOrderQuery() {
        return new DefaultOrderQuery();
    }

    @Override
    public List<DailyOrder> queryDailyOrders(OrderQuery query) {
        return this.orderReportRepository.queryDailyOrders(query);
    }

    @Override
    public List<MonthlyOrder> queryMonthlyOrders(OrderQuery query) {
        return this.orderReportRepository.queryMonthlyOrders(query);
    }

    @Override
    public TotalOrders queryTotalOrders(OrderQuery query) {
        return this.orderReportRepository.queryTotalOrders(query);
    }
}
