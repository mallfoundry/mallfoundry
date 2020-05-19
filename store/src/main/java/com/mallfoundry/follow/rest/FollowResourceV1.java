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

package com.mallfoundry.follow.rest;

import com.mallfoundry.follow.DefaultFollowService;
import com.mallfoundry.follow.FollowProduct;
import com.mallfoundry.store.product.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class FollowResourceV1 {

    private final DefaultFollowService followService;

    private final ProductService productService;

    public FollowResourceV1(DefaultFollowService followService, ProductService productService) {
        this.followService = followService;
        this.productService = productService;
    }

    @PostMapping("/customer/following-products/{product_id}")
    public FollowProduct followingProduct(@PathVariable("product_id") String productId) {
        return this.followService.followingProduct(productId);
    }

    @DeleteMapping("/customer/following-products/{product_id}")
    public void unfollowingProduct(@PathVariable("product_id") String productId) {
        this.followService.unfollowingProduct(productId);
    }

    @GetMapping("/customer/following-products/{product_id}")
    public boolean checkFollowingProduct(@PathVariable("product_id") String productId) {
        return this.followService.checkFollowingProduct(productId);
    }
//
//    @GetMapping("/customers/{customer_id}/following_products")
//    public List<String> getFollowProducts(@PathVariable("customer_id") String customerId) {
//        return this.followService.getFollowingProducts(new CustomerId(customerId));
//    }
//
//    @GetMapping("/customers/{customer_id}/following_products/count")
//    public long getFollowingProductCount(@PathVariable("customer_id") String customerId) {
//        return this.followService.getFollowingProductCount(new CustomerId(customerId));
//    }
//
//    @GetMapping("/products/{product_id}/followers/count")
//    public long getProductFollowerCount(@PathVariable("product_id") String productId) {
//        return this.followService.getProductFollowerCount(new InternalProductId(productId));
//    }
//
//    @PostMapping("/customers/{customer_id}/following_stores/{store_id}")
//    public void followStore(@PathVariable("customer_id") String customerId,
//                            @PathVariable("store_id") String storeId) {
//        this.followService.followStore(new CustomerId(customerId), new InternalStoreId(storeId));
//    }
//
//    @DeleteMapping("/customers/{customer_id}/following_stores/{store_id}")
//    public void unfollowStore(@PathVariable("customer_id") String customerId,
//                              @PathVariable("store_id") String storeId) {
//        this.followService.unfollowStore(new CustomerId(customerId), new InternalStoreId(storeId));
//    }
//
//    @GetMapping("/customers/{customer_id}/following_stores/{store_id}")
//    public boolean isFollowingStore(@PathVariable("customer_id") String customerId,
//                                    @PathVariable("store_id") String storeId) {
//        return this.followService.isFollowingStore(new CustomerId(customerId), new InternalStoreId(storeId));
//    }
//
//    @GetMapping("/customers/{customer_id}/following_stores")
//    public List<String> getFollowStores(@PathVariable("customer_id") String customerId) {
//        return this.followService.getFollowStores(new CustomerId(customerId));
//    }
//
//    @GetMapping("/customers/{customer_id}/following_stores/count")
//    public long getFollowingStoreCount(@PathVariable("customer_id") String customerId) {
//        return this.followService.getFollowingStoreCount(new CustomerId(customerId));
//    }
//
//    @GetMapping("/stores/{store_id}/followers/count")
//    public long getStoreFollowerCount(@PathVariable("store_id") String storeId) {
//        return this.followService.getStoreFollowerCount(new InternalStoreId(storeId));
//    }
}
