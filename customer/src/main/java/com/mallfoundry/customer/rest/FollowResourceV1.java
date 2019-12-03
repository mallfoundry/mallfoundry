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

package com.mallfoundry.customer.rest;

import com.mallfoundry.customer.application.FollowService;
import com.mallfoundry.customer.domain.follow.FollowProduct;
import com.mallfoundry.customer.domain.follow.FollowStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class FollowResourceV1 {

    private final FollowService followService;

    public FollowResourceV1(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/customers/{customer_id}/follow_products/{product_id}")
    public void followProduct(@PathVariable("customer_id") String customerId,
                              @PathVariable("product_id") String productId) {
        this.followService.followProduct(new FollowProduct(customerId, productId));
    }

    @DeleteMapping("/customers/{customer_id}/follow_products/{product_id}")
    public void unfollowProduct(@PathVariable("customer_id") String customerId,
                                @PathVariable("product_id") String productId) {
        this.followService.unfollowProduct(new FollowProduct(customerId, productId));
    }

    @GetMapping("/customers/{customer_id}/follow_products/{product_id}")
    public boolean isFollowingProduct(@PathVariable("customer_id") String customerId,
                                      @PathVariable("product_id") String productId) {
        return this.followService.isFollowingProduct(new FollowProduct(customerId, productId));
    }

    @GetMapping("/customers/{customer_id}/follow_products")
    public List<String> getFollowProducts(@PathVariable("customer_id") String customerId) {
        return this.followService.getFollowingProducts(customerId);
    }

    @GetMapping("/customers/{customer_id}/follow_products/count")
    public int getFollowingProductCount(@PathVariable("customer_id") String customerId) {
        return this.followService.getFollowingProductCount(customerId);
    }

    @PostMapping("/customers/{customer_id}/follow_stores/{store_id}")
    public void followStore(@PathVariable("customer_id") String customerId,
                            @PathVariable("store_id") String storeId) {
        this.followService.followStore(new FollowStore(customerId, storeId));
    }

    @DeleteMapping("/customers/{customer_id}/follow_stores/{store_id}")
    public void unfollowStore(@PathVariable("customer_id") String customerId,
                              @PathVariable("store_id") String storeId) {
        this.followService.unfollowStore(new FollowStore(customerId, storeId));
    }

    @GetMapping("/customers/{customer_id}/follow_stores/{store_id}")
    public boolean isFollowingStore(@PathVariable("customer_id") String customerId,
                                    @PathVariable("store_id") String storeId) {
        return this.followService.isFollowingStore(new FollowStore(customerId, storeId));
    }

    @GetMapping("/customers/{customer_id}/follow_stores")
    public List<String> getFollowStores(@PathVariable("customer_id") String customerId) {
        return this.followService.getFollowStores(customerId);
    }

    @GetMapping("/customers/{customer_id}/follow_stores/count")
    public int getFollowingStoreCount(@PathVariable("customer_id") String customerId) {
        return this.followService.getFollowingStoreCount(customerId);
    }
}
