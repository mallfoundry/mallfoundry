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

package org.mallfoundry.follow;

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DefaultFollowService implements FollowService {

    private final ProductService productService;

    private final StoreService storeService;

    private final FollowProductRepository followProductRepository;

    private final FollowStoreRepository followStoreRepository;

    public DefaultFollowService(ProductService productService,
                                StoreService storeService,
                                FollowProductRepository followProductRepository,
                                FollowStoreRepository followStoreRepository) {
        this.productService = productService;
        this.storeService = storeService;
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
    public FollowProduct followingProduct(String followerId, String productId) {
        var id = new InternalFollowProductId(followerId, productId);
        if (this.followProductRepository.existsById(id)) {
            throw new FollowException("The follower has followed to this product");
        }
        var product = this.productService.getProduct(productId).orElseThrow();
        var followProduct = new InternalFollowProduct(followerId, productId);
        followProduct.setImageUrl(CollectionUtils.firstElement(product.getImageUrls()));
        followProduct.setName(product.getName());
        followProduct.setStatus(product.getStatus());
        followProduct.setStoreId(product.getStoreId());
        return this.followProductRepository.save(followProduct);
    }

    @Override
    public void unfollowingProduct(String followerId, String productId) {
        var id = new InternalFollowProductId(followerId, productId);
        var followProduct = this.followProductRepository.findById(id).orElseThrow();
        this.followProductRepository.delete(followProduct);
    }

    @Override
    public boolean checkFollowingProduct(String followerId, String productId) {
        var id = new InternalFollowProductId(followerId, productId);
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
    public long getProductFollowerCount(String productId) {
        return this.followProductRepository.countById(productId);
    }

    @Override
    public void followingStore(String followerId, String storeId) {
        var id = new InternalFollowStoreId(followerId, storeId);
        if (this.followStoreRepository.existsById(id)) {
            throw new FollowException("The follower has followed to this store");
        }
        var store = this.storeService.getStore(storeId).orElseThrow();
        var followStore = new InternalFollowStore(followerId, storeId);
        followStore.setLogoUrl(store.getLogoUrl());
        followStore.setName(store.getName());
        this.followStoreRepository.save(followStore);
    }

    @Override
    public void unfollowingStore(String followerId, String storeId) {
        var id = new InternalFollowStoreId(followerId, storeId);
        var followStore = this.followStoreRepository.findById(id).orElseThrow();
        this.followStoreRepository.delete(followStore);
    }

    @Override
    public boolean checkFollowingStore(String followerId, String storeId) {
        var id = new InternalFollowStoreId(followerId, storeId);
        return this.followStoreRepository.existsById(id);
    }

    @Override
    public SliceList<FollowStore> getFollowingStores(FollowStoreQuery query) {
        return CastUtils.cast(this.followStoreRepository.findAll(query));
    }

    @Override
    public long getFollowingStoreCount(FollowStoreQuery query) {
        return this.followStoreRepository.count(query);
    }

    @Override
    public long getStoreFollowerCount(String storeId) {
        return this.followStoreRepository.countById(storeId);
    }

}
