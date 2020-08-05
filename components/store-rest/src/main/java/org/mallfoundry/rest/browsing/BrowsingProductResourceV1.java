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

package org.mallfoundry.rest.browsing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.browsing.BrowsingProduct;
import org.mallfoundry.browsing.BrowsingProductService;
import org.mallfoundry.data.SliceList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Tag(name = "BrowsingProduct Resource V1", description = "顾客浏览商品记录资源")
@RestController
@RequestMapping("/v1")
public class BrowsingProductResourceV1 {

    private final BrowsingProductService browsingProductService;

    public BrowsingProductResourceV1(BrowsingProductService browsingProductService) {
        this.browsingProductService = browsingProductService;
    }

    @Operation(summary = "添加一个顾客浏览的商品记录对象")
    @PostMapping("customers/{customer_id}/browsing-products/{product_id}")
    public BrowsingProduct hitBrowsingProduct(@PathVariable("customer_id") String customerId,
                                              @PathVariable("product_id") String productId) {
        return this.browsingProductService.hitBrowsingProduct(customerId, productId);
    }

    @Operation(summary = "获得顾客浏览的商品记录分页集合")
    @GetMapping("customers/{customer_id}/browsing-products")
    public SliceList<BrowsingProduct> getBrowsingProducts(
            @PathVariable("customer_id") String customerId,
            @RequestParam(name = "browsing_time", required = false) Date browsingTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        return this.browsingProductService.getBrowsingProducts(
                this.browsingProductService.createBrowsingProductQuery().toBuilder()
                        .page(page).limit(limit)
                        .browserId(customerId).browsingTime(browsingTime).build());
    }

    @Operation(summary = "获得顾客浏览的商品数量")
    @GetMapping("customers/{customer_id}/browsing-products/count")
    public long countBrowsingProducts(@PathVariable("customer_id") String customerId,
                                        @RequestParam(name = "browsing_time", required = false) Date browsingTime) {
        return this.browsingProductService.countBrowsingProducts(
                this.browsingProductService.createBrowsingProductQuery().toBuilder()
                        .browserId(customerId).browsingTime(browsingTime).build());
    }

    @Operation(summary = "根据标识删除顾客浏览的商品记录")
    @DeleteMapping("customers/{customer_id}/browsing-products/{id}")
    public void deleteBrowsingProduct(@PathVariable("customer_id") String customerId, @PathVariable("id") String id) {
        this.browsingProductService.deleteBrowsingProduct(customerId, id);
    }

    @Operation(summary = "批量删除顾客浏览的商品记录")
    @DeleteMapping("customers/{customer_id}/browsing-products/batch")
    public void deleteBrowsingProducts(@PathVariable("customer_id") String customerId, @RequestBody List<String> ids) {
        this.browsingProductService.deleteBrowsingProducts(customerId, ids);
    }
}
