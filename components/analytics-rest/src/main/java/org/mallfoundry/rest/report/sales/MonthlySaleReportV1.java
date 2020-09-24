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

package org.mallfoundry.rest.report.sales;

import org.mallfoundry.report.sales.MonthlySale;
import org.mallfoundry.report.sales.MonthlySaleReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reports")
public class MonthlySaleReportV1 {

    private final MonthlySaleReport monthlySaleReport;

    public MonthlySaleReportV1(MonthlySaleReport monthlySaleReport) {
        this.monthlySaleReport = monthlySaleReport;
    }

    @GetMapping("/monthly-sales")
    public List<MonthlySale> queryMonthlySales(@RequestParam Short year, @RequestParam Byte month,
                                               @RequestParam(required = false, name = "tenant_id") String tenantId,
                                               @RequestParam(required = false, name = "store_id") String storeId,
                                               @RequestParam(required = false, name = "customer_id") String customerId) {
        return this.monthlySaleReport.queryMonthlySales(
                this.monthlySaleReport.createMonthlySaleQuery().toBuilder()
                        .year(year).month(month)
                        .tenantId(tenantId).storeId(storeId).customerId(customerId)
                        .build());
    }
}
