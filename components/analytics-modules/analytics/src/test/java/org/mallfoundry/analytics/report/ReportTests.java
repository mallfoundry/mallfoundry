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

package org.mallfoundry.analytics.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportTests {

    @Autowired
    private ReportService reportService;

    @Test
    public void testCreateReport() {
        var report = this.reportService.createReport("order_sales")
                .toBuilder()
                .name("Order Sales")
                .statement("SELECT id_ FROM mf_catalog_product where store_id_ = :store_id")
                .statementType(ReportStatementType.SQL)
                .build();
        var savedReport = this.reportService.saveReport(report);
        System.out.println(savedReport);
    }

    @Test
    public void testGetReport() {

    }

    @Test
    public void testQueryReport() {
        var query = this.reportService.createReportQuery("order_sales").toBuilder().parameter("store_id", "mi").build();
        Object object = this.reportService.queryReport(query);
        System.out.println(object);
    }
}
