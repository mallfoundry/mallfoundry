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

package com.mallfoundry.follow;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.security.SecurityUserHolder;
import com.mallfoundry.store.StoreId;
import com.mallfoundry.store.product.ProductService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

@Service
public class DefaultFollowService implements FollowService {

    private final ProductService productService;

    private final FollowProductRepository followProductRepository;

    private final FollowStoreRepository followStoreRepository;

    public DefaultFollowService(ProductService productService,
                                FollowProductRepository followProductRepository,
                                FollowStoreRepository followStoreRepository) {
        this.productService = productService;
        this.followProductRepository = followProductRepository;
        this.followStoreRepository = followStoreRepository;
    }

    @Override
    public FollowerId createFollowerId(String id) {
        return new InternalFollowerId(id);
    }

    @Override
    public FollowProductQuery createFollowProductQuery() {
        return new InternalFollowProductQuery();
    }

    @Override
    public FollowStoreQuery createFollowStoreQuery() {
        return new InternalFollowStoreQuery();
    }

    @Override
    public FollowProduct followingProduct(String productId) {
        var followerId = SecurityUserHolder.getUserId();
        var id = new InternalFollowProductId(followerId, productId);
        if (this.followProductRepository.existsById(id)) {
            throw new FollowException("The follower has followed to this product");
        }
        var product = this.productService.getProduct(productId).orElseThrow();
        var followProduct = new InternalFollowProduct(followerId, productId);
        followProduct.setImageUrl(product.getFirstImageUrl());
        followProduct.setName(product.getName());
        followProduct.setStatus(product.getStatus());
        return this.followProductRepository.save(followProduct);
    }

    @Override
    public void unfollowingProduct(String productId) {
        var id = new InternalFollowProductId(SecurityUserHolder.getUserId(), productId);
        var followProduct = this.followProductRepository.findById(id).orElseThrow();
        this.followProductRepository.delete(followProduct);
    }

    @Override
    public boolean checkFollowingProduct(String productId) {
        var id = new InternalFollowProductId(SecurityUserHolder.getUserId(), productId);
        return this.followProductRepository.existsById(id);
    }

    @Override
    public SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query) {
        return CastUtils.cast(this.followProductRepository.findAll(query));
    }

    @Override
    public long getFollowingProductCount(FollowProductQuery query) {
        return this.followProductRepository.count(query);
    }

    @Override
    public void followingStore(FollowerId followerId, StoreId storeId) {
//        var mapping = new FollowStoreMapping(followerId.getIdentifier(), storeId.getIdentifier());
//        if (!this.followStoreRepository.exists(mapping)) {
//            throw new FollowException("The follower has followed to this store");
//        }
//        this.followStoreRepository.save(mapping);
    }

    @Override
    public void unfollowingStore(FollowerId followerId, StoreId storeId) {
        var id = new InternalFollowStoreId(followerId.getIdentifier(), storeId.getIdentifier());
        var followStore = this.followStoreRepository.findById(id).orElseThrow();
        this.followStoreRepository.delete(followStore);
    }

    @Override
    public boolean checkFollowingStore(FollowerId followerId, StoreId storeId) {
        var id = new InternalFollowStoreId(followerId.getIdentifier(), storeId.getIdentifier());
        return this.followStoreRepository.existsById(id);
    }

    @Override
    public SliceList<FollowStore> getFollowingStores(FollowStoreQuery query) {
        return null;
    }

    @Override
    public long getFollowingStoreCount(FollowStoreQuery query) {
        return 0;
    }

}
