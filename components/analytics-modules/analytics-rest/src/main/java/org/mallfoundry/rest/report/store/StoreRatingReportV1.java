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

package org.mallfoundry.rest.report.store;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.report.store.StoreRatingReport;
import org.mallfoundry.report.store.TotalStoreRatings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Store Reports")
@RestController
@RequestMapping("/v1/reports")
public class StoreRatingReportV1 {

    private final StoreRatingReport storeRatingReport;

    public StoreRatingReportV1(StoreRatingReport storeRatingReport) {
        this.storeRatingReport = storeRatingReport;
    }

    @GetMapping("/total-store-ratings")
    public TotalStoreRatings queryTotalStoreRatings(@RequestParam(required = false) Short year,
                                                    @RequestParam(required = false) Byte month,
                                                    @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                                    @RequestParam(required = false, name = "date_to") Integer dateTo,
                                                    @RequestParam(required = false, name = "tenant_id") String tenantId,
                                                    @RequestParam(required = false, name = "store_id") String storeId) {
        return this.storeRatingReport.queryTotalStoreRatings(
                this.storeRatingReport.createStoreRatingQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId)
                        .build());
    }
}
