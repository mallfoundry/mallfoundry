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

package org.mallfoundry.rest.report.catalog;

import org.mallfoundry.report.catalog.ProductRatingReport;
import org.mallfoundry.report.catalog.TotalProductRatingCounts;
import org.mallfoundry.report.catalog.TotalProductRatings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reports")
public class ProductRatingReportV1 {

    private final ProductRatingReport productRatingReport;

    public ProductRatingReportV1(ProductRatingReport productRatingReport) {
        this.productRatingReport = productRatingReport;
    }

    @GetMapping("/total-product-rating-counts")
    public TotalProductRatingCounts queryTotalProductRatingCounts(@RequestParam(required = false) Short year,
                                                                  @RequestParam(required = false) Byte month,
                                                                  @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                                                  @RequestParam(required = false, name = "date_to") Integer dateTo,
                                                                  @RequestParam(required = false, name = "tenant_id") String tenantId,
                                                                  @RequestParam(required = false, name = "store_id") String storeId) {
        return this.productRatingReport.queryTotalProductRatingCounts(
                this.productRatingReport.createProductRatingQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId)
                        .build());
    }

    @GetMapping("/total-product-ratings")
    public TotalProductRatings queryTotalProductRatings(@RequestParam(required = false) Short year,
                                                        @RequestParam(required = false) Byte month,
                                                        @RequestParam(required = false, name = "date_from") Integer dateFrom,
                                                        @RequestParam(required = false, name = "date_to") Integer dateTo,
                                                        @RequestParam(required = false, name = "tenant_id") String tenantId,
                                                        @RequestParam(required = false, name = "store_id") String storeId) {
        return this.productRatingReport.queryTotalProductRatings(
                this.productRatingReport.createProductRatingQuery().toBuilder()
                        .year(year).month(month)
                        .dateFrom(dateFrom).dateTo(dateTo)
                        .tenantId(tenantId).storeId(storeId)
                        .build());
    }
}
