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

package org.mallfoundry.rest.report.order;

import org.mallfoundry.report.order.DailyOrder;
import org.mallfoundry.report.order.MonthlyOrder;
import org.mallfoundry.report.order.OrderReport;
import org.mallfoundry.report.order.TotalOrders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reports")
public class OrderReportV1 {

    private final OrderReport orderReport;

    public OrderReportV1(OrderReport orderReport) {
        this.orderReport = orderReport;
    }

    @GetMapping("/daily-orders")
    public List<DailyOrder> queryDailyOrders(@RequestParam(required = false) Short year,
                                             @RequestParam(required = false) Byte month,
                                             @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                             @RequestParam(required = false, name = "date_to") Integer dateTo,
                                             @RequestParam(required = false, name = "tenant_id") String tenantId,
                                             @RequestParam(required = false, name = "store_id") String storeId,
                                             @RequestParam(required = false, name = "customer_id") String customerId) {
        return this.orderReport.queryDailyOrders(
                this.orderReport.createOrderQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId).customerId(customerId)
                        .build());
    }

    @GetMapping("/monthly-orders")
    public List<MonthlyOrder> queryMonthlyOrders(@RequestParam(required = false) Short year,
                                                 @RequestParam(required = false) Byte month,
                                                 @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                                 @RequestParam(required = false, name = "date_to") Integer dateTo,
                                                 @RequestParam(required = false, name = "tenant_id") String tenantId,
                                                 @RequestParam(required = false, name = "store_id") String storeId,
                                                 @RequestParam(required = false, name = "customer_id") String customerId) {
        return this.orderReport.queryMonthlyOrders(
                this.orderReport.createOrderQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId).customerId(customerId)
                        .build());
    }

    @GetMapping("/total-orders")
    public TotalOrders queryTotalOrders(@RequestParam(required = false) Short year,
                                        @RequestParam(required = false) Byte month,
                                        @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                        @RequestParam(required = false, name = "date_to") Integer dateTo,
                                        @RequestParam(required = false, name = "tenant_id") String tenantId,
                                        @RequestParam(required = false, name = "store_id") String storeId,
                                        @RequestParam(required = false, name = "customer_id") String customerId) {
        return this.orderReport.queryTotalOrders(
                this.orderReport.createOrderQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId).customerId(customerId)
                        .build());
    }
}
