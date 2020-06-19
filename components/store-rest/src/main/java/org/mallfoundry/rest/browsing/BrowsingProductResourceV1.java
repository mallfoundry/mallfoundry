/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    public BrowsingProduct addBrowsingProduct(@PathVariable("customer_id") String customerId,
                                              @PathVariable("product_id") String productId) {
        return this.browsingProductService.addBrowsingProduct(customerId, productId);
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
    public long getBrowsingProductCount(@PathVariable("customer_id") String customerId,
                                        @RequestParam(name = "browsing_time", required = false) Date browsingTime) {
        return this.browsingProductService.getBrowsingProductCount(
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
