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
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultFollowService implements FollowService {

    private final ProductService productService;

    private final StoreService storeService;

    private final FollowProductRepository followProductRepository;

    private final StoreFollowingRepository storeFollowingRepository;

    public DefaultFollowService(ProductService productService,
                                StoreService storeService,
                                FollowProductRepository followProductRepository,
                                StoreFollowingRepository storeFollowingRepository) {
        this.productService = productService;
        this.storeService = storeService;
        this.followProductRepository = followProductRepository;
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
    public FollowProduct followProduct(String followerId, String productId) {
        var id = new JpaFollowProductId(followerId, productId);
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
        var id = new JpaFollowProductId(followerId, productId);
        var followProduct = this.followProductRepository.findById(id).orElseThrow();
        this.followProductRepository.delete(followProduct);
    }

    @Override
    public boolean checkFollowingProduct(String followerId, String productId) {
        var id = new JpaFollowProductId(followerId, productId);
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

    @Override
    public SliceList<FollowStore> getFollowingStores(FollowStoreQuery query) {
        var sliceFollowings = this.storeFollowingRepository.findAll(query);
        var storeIds = ListUtils.emptyIfNull(sliceFollowings.getElements()).stream()
                .map(StoreFollowing::getStoreId)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isEmpty(storeIds)) {
            return sliceFollowings.elements(Collections.emptyList());
        }
        // 根据店铺标识获得店铺对象集合。
        var stores = this.storeService.getStores(storeIds);
        var followStores = sliceFollowings.getElements().stream()
                .map(following -> stores.stream()
                        .filter(aStore -> Objects.equals(aStore.getId(), following.getStoreId()))
                        .findFirst()
                        .<FollowStore>map(aStore -> new DelegatingFollowStore(aStore, following))
                        .orElseGet(() -> new NullFollowStore(following)))
                .collect(Collectors.toUnmodifiableList());
        return sliceFollowings.elements(followStores);
    }

    @Override
    public long countFollowingStores(FollowStoreQuery query) {
        return this.storeFollowingRepository.count(query);
    }
}
