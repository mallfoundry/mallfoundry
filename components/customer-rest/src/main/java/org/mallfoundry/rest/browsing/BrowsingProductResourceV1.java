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

@RestController
@RequestMapping("/v1")
public class BrowsingProductResourceV1 {

    private final BrowsingProductService browsingProductService;

    public BrowsingProductResourceV1(BrowsingProductService browsingProductService) {
        this.browsingProductService = browsingProductService;
    }

    @PostMapping("/browsing-products")
    public BrowsingProduct addBrowsingProduct(@RequestBody BrowsingProductRequest request) {
        var browsingProduct = this.browsingProductService.createBrowsingProduct()
                .toBuilder().productId(request.getProductId()).build();
        return this.browsingProductService.addBrowsingProduct(browsingProduct);
    }

    @DeleteMapping("/browsing-products/{id}")
    public void deleteBrowsingProduct(@PathVariable("id") String id) {
        this.browsingProductService.deleteBrowsingProduct(id);
    }

    @DeleteMapping("/browsing-products/batch")
    public void deleteBrowsingProducts(@RequestBody List<String> ids) {
        this.browsingProductService.deleteBrowsingProducts(ids);
    }

    @GetMapping("/browsing_products")
    public SliceList<BrowsingProduct> getBrowsingProducts(
            @RequestParam(name = "browser_id", required = false) String browserId,
            @RequestParam(name = "browsing_time", required = false) Date browsingTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int limit) {
        var query = this.browsingProductService.createBrowsingProductQuery()
                .toBuilder().page(page).limit(limit).browserId(browserId).browsingTime(browsingTime).build();
        return this.browsingProductService.getBrowsingProducts(query);
    }

    @GetMapping("/browsing_products/count")
    public long getBrowsingProductCount(@RequestParam(name = "browser_id", required = false) String browserId,
                                        @RequestParam(name = "browsing_time", required = false) Date browsingTime) {
        var query = this.browsingProductService.createBrowsingProductQuery()
                .toBuilder().browserId(browserId).browsingTime(browsingTime).build();
        return this.browsingProductService.getBrowsingProductCount(query);
    }

}
