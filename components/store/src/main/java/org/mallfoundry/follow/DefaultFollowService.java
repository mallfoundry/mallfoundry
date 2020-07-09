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
    public FollowProduct followProduct(String followerId, String productId) {
        var id = new InternalFollowProductId(followerId, productId);
        if (this.followProductRepository.existsById(id)) {
            throw new FollowException("The follower has followed to this product");
        }
        var product = this.productService.getProduct(productId).orElseThrow();
        var followProduct = new InternalFollowProduct(followerId, productId);
        followProduct.setImageUrl(CollectionUtils.firstElement(product.getImageUrls()));
        followProduct.setName(product.getName());
        followProduct.setPrice(product.getPrice());
        followProduct.setStatus(product.getStatus());
        followProduct.setStoreId(product.getStoreId());
        return this.followProductRepository.save(followProduct);
    }

    @Override
    public void unfollowProduct(String followerId, String productId) {
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
    public void followStore(String followerId, String storeId) {
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
    public void unfollowStore(String followerId, String storeId) {
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
