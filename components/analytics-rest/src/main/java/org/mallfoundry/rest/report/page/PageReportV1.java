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

package org.mallfoundry.rest.report.page;

import org.mallfoundry.report.page.DailyPage;
import org.mallfoundry.report.page.PageReport;
import org.mallfoundry.report.page.TotalPages;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reports")
public class PageReportV1 {

    private final PageReport pageReport;

    public PageReportV1(PageReport pageReport) {
        this.pageReport = pageReport;
    }

    @GetMapping("/daily-pages")
    public List<DailyPage> queryDailyPages(@RequestParam(name = "date_from") Integer dateFrom,
                                           @RequestParam(name = "date_to") Integer dateTo,
                                           @RequestParam(required = false, name = "tenant_id") String tenantId,
                                           @RequestParam(required = false, name = "store_id") String storeId,
                                           @RequestParam(required = false, name = "browser_id") String browserId) {
        return this.pageReport.queryDailyPages(
                this.pageReport.createPageQuery().toBuilder()
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId).browserId(browserId)
                        .build());
    }

    @GetMapping("/total-pages")
    public TotalPages queryTotalPages(@RequestParam(required = false, name = "date") Integer date,
                                      @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                      @RequestParam(required = false, name = "date_to") Integer dateTo,
                                      @RequestParam(required = false, name = "tenant_id") String tenantId,
                                      @RequestParam(required = false, name = "store_id") String storeId,
                                      @RequestParam(required = false, name = "browser_id") String browserId) {
        return this.pageReport.queryTotalPages(
                this.pageReport.createPageQuery().toBuilder()
                        .date(date).dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId).browserId(browserId)
                        .build());
    }
}
