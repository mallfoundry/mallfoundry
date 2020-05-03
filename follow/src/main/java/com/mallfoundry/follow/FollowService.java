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

import com.mallfoundry.customer.CustomerId;
import com.mallfoundry.store.InternalStoreId;
import com.mallfoundry.store.product.ProductId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowProductRepository followProductRepository;

    private final FollowStoreRepository followStoreRepository;

    public FollowService(FollowProductRepository followProductRepository,
                         FollowStoreRepository followStoreRepository) {
        this.followProductRepository = followProductRepository;
        this.followStoreRepository = followStoreRepository;
    }

    @Transactional
    public void followProduct(CustomerId customerId, ProductId productId) {
        if (this.isFollowingProduct(customerId, productId)) {
            throw new FollowException("The customer has followed to this product.");
        }
        this.followProductRepository.save(new FollowProduct(customerId, productId));
    }

    @Transactional
    public void unfollowProduct(CustomerId customerId, ProductId productId) {
        this.followProductRepository.findOne(Example.of(new FollowProduct(customerId, productId)))
                .ifPresent(this.followProductRepository::delete);
    }

    public boolean isFollowingProduct(CustomerId customerId, ProductId productId) {
        return this.followProductRepository.count(Example.of(new FollowProduct(customerId, productId))) > 0;
    }

    public List<Long> getFollowingProducts(CustomerId customerId) {
        return this.followProductRepository
                .findAll(Example.of(new FollowProduct(customerId)))
                .stream()
                .map(FollowProduct::getProductId)
                .map(ProductId::getIdentifier)
                .distinct()
                .collect(Collectors.toList());
    }

    public long getFollowingProductCount(CustomerId customerId) {
        return this.followProductRepository.count(Example.of(new FollowProduct(customerId)));
    }

    public long getProductFollowerCount(ProductId productId) {
        return this.followProductRepository.count(Example.of(new FollowProduct(productId)));
    }

    @Transactional
    public void followStore(CustomerId customerId, InternalStoreId storeId) {
        this.followStoreRepository.save(new FollowStore(customerId, storeId));
    }

    @Transactional
    public void unfollowStore(CustomerId customerId, InternalStoreId storeId) {
        this.followStoreRepository.findOne(Example.of(new FollowStore(customerId, storeId)))
                .ifPresent(this.followStoreRepository::delete);
    }

    public boolean isFollowingStore(CustomerId customerId, InternalStoreId storeId) {
        return this.followStoreRepository.count(Example.of(new FollowStore(customerId, storeId))) > 0;
    }

    public List<String> getFollowStores(CustomerId customerId) {
        return this.followStoreRepository
                .findAll(Example.of(new FollowStore(customerId)))
                .stream()
                .map(FollowStore::getStoreId)
                .map(InternalStoreId::toString)
                .collect(Collectors.toList());
    }

    public long getFollowingStoreCount(CustomerId customerId) {
        return this.followStoreRepository.count(Example.of(new FollowStore(customerId)));
    }

    public long getStoreFollowerCount(InternalStoreId storeId) {
        return this.followStoreRepository.count(Example.of(new FollowStore(storeId)));
    }

}
