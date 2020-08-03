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

package org.mallfoundry.following;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultFollowService implements FollowService {

    private final ProductService productService;

    private final StoreService storeService;

    private final ProductFollowingRepository productFollowingRepository;

    private final StoreFollowingRepository storeFollowingRepository;

    public DefaultFollowService(ProductService productService,
                                StoreService storeService,
                                ProductFollowingRepository productFollowingRepository,
                                StoreFollowingRepository storeFollowingRepository) {
        this.productService = productService;
        this.storeService = storeService;
        this.productFollowingRepository = productFollowingRepository;
        this.storeFollowingRepository = storeFollowingRepository;
    }

    @Override
    public FollowProductQuery createFollowProductQuery() {
        return new DefaultFollowProductQuery();
    }

    @Override
    public FollowStoreQuery createFollowStoreQuery() {
        return new DefaultFollowStoreQuery();
    }

    @Override
    public void followProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        if (this.productFollowingRepository.exists(following)) {
            throw new FollowException("The follower has followed to this product");
        }
        this.productFollowingRepository.save(following);
    }

    @Override
    public void unfollowProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        this.productFollowingRepository.delete(following);
    }

    @Override
    public boolean checkFollowingProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        return this.productFollowingRepository.exists(following);
    }

    private FollowProduct getFollowProduct(List<Product> products, ProductFollowing following) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), following.getProductId()))
                .findFirst()
                .<FollowProduct>map(aStore -> new DelegatingImmutableFollowProduct(aStore, following))
                .orElseGet(() -> new NullFollowProduct(following));
    }

    @Override
    public SliceList<FollowProduct> getFollowingProducts(FollowProductQuery query) {
        var sliceFollowings = this.productFollowingRepository.findAll(query);
        var productIds = ListUtils.emptyIfNull(sliceFollowings.getElements()).stream()
                .map(ProductFollowing::getProductId)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isEmpty(productIds)) {
            return sliceFollowings.elements(Collections.emptyList());
        }
        var products = this.productService.getProducts(productIds);
        var followProducts = sliceFollowings.getElements().stream()
                .map(following -> this.getFollowProduct(products, following))
                .collect(Collectors.toUnmodifiableList());
        return sliceFollowings.elements(followProducts);
    }

    @Override
    public long countFollowingProducts(FollowProductQuery query) {
        return this.productFollowingRepository.count(query);
    }

    @Override
    public void followStore(String followerId, String storeId) {
        var following = this.storeFollowingRepository.create(followerId, storeId);
        if (this.storeFollowingRepository.exists(following)) {
            throw new FollowException("The follower has followed to this store");
        }
        following.following();
        this.storeFollowingRepository.save(following);
    }

    @Override
    public void unfollowStore(String followerId, String storeId) {
        var following = this.storeFollowingRepository.create(followerId, storeId);
        this.storeFollowingRepository.delete(following);
    }

    @Override
    public boolean checkFollowingStore(String followerId, String storeId) {
        var following = this.storeFollowingRepository.create(followerId, storeId);
        return this.storeFollowingRepository.exists(following);
    }

    private FollowStore getFollowStore(List<Store> stores, StoreFollowing following) {
        return stores.stream()
                .filter(aStore -> Objects.equals(aStore.getId(), following.getStoreId()))
                .findFirst()
                .<FollowStore>map(aStore -> new DelegatingImmutableFollowStore(aStore, following))
                .orElseGet(() -> new NullFollowStore(following));
    }

    @Override
    public SliceList<FollowStore> getFollowingStores(FollowStoreQuery query) {
        var sliceFollowings = this.storeFollowingRepository.findAll(query);
        var storeIds = ListUtils.emptyIfNull(sliceFollowings.getElements()).stream()
                .map(StoreFollowing::getStoreId)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isEmpty(storeIds)) {
            return sliceFollowings.elements(Collections.emptyList());
        }
        var stores = this.storeService.getStores(storeIds);
        var followStores = sliceFollowings.getElements().stream()
                .map(following -> this.getFollowStore(stores, following))
                .collect(Collectors.toUnmodifiableList());
        return sliceFollowings.elements(followStores);
    }

    @Override
    public long countFollowingStores(FollowStoreQuery query) {
        return this.storeFollowingRepository.count(query);
    }
}
