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

package com.mallfoundry.rest.browsing;

import com.mallfoundry.browsing.BrowsingProduct;
import com.mallfoundry.browsing.BrowsingProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class BrowsingProductResourceV1 {

    private final BrowsingProductService browsingProductService;

    public BrowsingProductResourceV1(BrowsingProductService browsingProductService) {
        this.browsingProductService = browsingProductService;
    }

    @PostMapping("/browsing-products")
    public BrowsingProduct saveBrowsingProduct(@RequestBody BrowsingProductRequest request) {
        return this.browsingProductService.saveBrowsingProduct(this.browsingProductService.createBrowsingProduct(request.getBrowserId(), request.getProductId()));
    }

    @DeleteMapping("/browsing-products/{id}")
    public void deleteBrowsingProduct(@PathVariable("id") String id) {
        this.browsingProductService.deleteBrowsingProduct(id);
    }

    @DeleteMapping("/browsing-products/batch")
    public void deleteBrowsingProducts(@RequestBody List<String> ids) {
        this.browsingProductService.deleteBrowsingProducts(ids);
    }

//    @GetMapping("/customers/{customer_id}/browsing_products")
//    public OffsetList<BrowsingProduct> getBrowsingProducts(
//            @PathVariable("customer_id") String customerId,
//            @RequestParam(required = false, name = "browsing_time") Date browsingTime,
//            @RequestParam(defaultValue = "0") int offset,
//            @RequestParam(defaultValue = "20") int limit) {
//        return this.browsingProductService
//                .getBrowsingProducts(
//                        BrowsingProductQuery
//                                .builder()
//                                .customerId(customerId)
//                                .browsingTime(browsingTime)
//                                .offset(offset)
//                                .limit(limit)
//                                .build());
//    }

    @GetMapping("/customers/{customer_id}/browsing_products/count")
    public long getBrowsingProductCount(@PathVariable("customer_id") String customerId) {
//        return this.browsingProductService.getBrowsingProductCount(customerId);
        return 0;
    }

}
