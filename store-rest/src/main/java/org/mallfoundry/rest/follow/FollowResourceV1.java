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

package org.mallfoundry.rest.follow;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.follow.FollowProduct;
import org.mallfoundry.follow.FollowService;
import org.mallfoundry.follow.FollowStore;
import org.mallfoundry.security.SecurityUserHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class FollowResourceV1 {

    private final FollowService followService;

    public FollowResourceV1(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/customer/following-products/{product_id}")
    public FollowProduct followingProduct(@PathVariable("product_id") String productId) {
        return this.followService.followingProduct(SecurityUserHolder.getUserId(), productId);
    }

    @DeleteMapping("/customer/following-products/{product_id}")
    public void unfollowingProduct(@PathVariable("product_id") String productId) {
        this.followService.unfollowingProduct(SecurityUserHolder.getUserId(), productId);
    }

    @GetMapping("/customer/following-products/{product_id}")
    public boolean checkFollowingProduct(@PathVariable("product_id") String productId) {
        return this.followService.checkFollowingProduct(SecurityUserHolder.getUserId(), productId);
    }

    @GetMapping("/customer/following-products")
    public SliceList<FollowProduct> getFollowProducts() {
        return this.followService.getFollowingProducts(this.followService.createFollowProductQuery()
                .toBuilder().followerId(SecurityUserHolder.getUserId()).build());
    }

    @GetMapping("/customer/following-products/count")
    public long getFollowingProductCount() {
        return this.followService.getFollowingProductCount(this.followService.createFollowProductQuery()
                .toBuilder().followerId(SecurityUserHolder.getUserId()).build());
    }

    @GetMapping("/products/{product_id}/followers/count")
    public long getProductFollowerCount(@PathVariable("product_id") String productId) {
        return this.followService.getProductFollowerCount(productId);
    }

    @PostMapping("/customer/following-stores/{store_id}")
    public void followingStore(@PathVariable("store_id") String storeId) {
        this.followService.followingStore(SecurityUserHolder.getUserId(), storeId);
    }

    @DeleteMapping("/customer/following-stores/{store_id}")
    public void unfollowingStore(@PathVariable("store_id") String storeId) {
        this.followService.unfollowingStore(SecurityUserHolder.getUserId(), storeId);
    }

    @GetMapping("/customer/following-stores/{store_id}")
    public boolean checkFollowingStore(@PathVariable("store_id") String storeId) {
        return this.followService.checkFollowingStore(SecurityUserHolder.getUserId(), storeId);
    }

    @GetMapping("/customer/following-stores")
    public SliceList<FollowStore> getFollowStores() {
        return this.followService.getFollowingStores(this.followService.createFollowStoreQuery()
                .toBuilder().followerId(SecurityUserHolder.getUserId()).build());
    }

    @GetMapping("/customer/following-stores/count")
    public long getFollowingStoreCount() {
        return this.followService.getFollowingStoreCount(this.followService.createFollowStoreQuery()
                .toBuilder().followerId(SecurityUserHolder.getUserId()).build());
    }

    @GetMapping("/stores/{store_id}/followers/count")
    public long getStoreFollowerCount(@PathVariable("store_id") String storeId) {
        return this.followService.getStoreFollowerCount(storeId);
    }
}
