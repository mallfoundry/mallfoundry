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

import org.mallfoundry.customer.BrowsingProduct;
import org.mallfoundry.customer.BrowsingProductService;
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

@RestController
@RequestMapping("/v1")
public class BrowsingProductResourceV1 {

    private final BrowsingProductService browsingProductService;

    public BrowsingProductResourceV1(BrowsingProductService browsingProductService) {
        this.browsingProductService = browsingProductService;
    }

    @PostMapping("customers/{customer_id}/browsing-products")
    public BrowsingProduct addBrowsingProduct(@PathVariable("customer_id") String customerId,
                                              @RequestBody BrowsingProductRequest request) {
        return this.browsingProductService.addBrowsingProduct(
                this.browsingProductService.createBrowsingProduct(request.getId())
                        .toBuilder().browserId(customerId).name(request.getName()).price(request.getPrice()).build());
    }

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

    @GetMapping("customers/{customer_id}/browsing-products/count")
    public long getBrowsingProductCount(@PathVariable("customer_id") String customerId,
                                        @RequestParam(name = "browsing_time", required = false) Date browsingTime) {
        return this.browsingProductService.getBrowsingProductCount(
                this.browsingProductService.createBrowsingProductQuery().toBuilder()
                        .browserId(customerId).browsingTime(browsingTime).build());
    }

    @DeleteMapping("customers/{customer_id}/browsing-products/{id}")
    public void deleteBrowsingProduct(@PathVariable("customer_id") String customerId, @PathVariable("id") String id) {
        this.browsingProductService.deleteBrowsingProduct(customerId, id);
    }

    @DeleteMapping("customers/{customer_id}/browsing-products/batch")
    public void deleteBrowsingProducts(@PathVariable("customer_id") String customerId, @RequestBody List<String> ids) {
        this.browsingProductService.deleteBrowsingProducts(customerId, ids);
    }
}
