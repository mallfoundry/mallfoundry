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

package com.mallfoundry.customer.follow;

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
    public void followProduct(FollowProduct followProduct) {
        this.followProductRepository.save(followProduct);
    }

    @Transactional
    public void unfollowProduct(FollowProduct followProduct) {
        this.followProductRepository.findOne(Example.of(followProduct))
                .ifPresent(this.followProductRepository::delete);
    }

    public boolean isFollowingProduct(FollowProduct followProduct) {
        return this.followProductRepository.count(Example.of(followProduct)) > 0;
    }

    public List<String> getFollowingProducts(String customerId) {
        return this.followProductRepository
                .findAll(Example.of(new FollowProduct(customerId)))
                .stream()
                .map(FollowProduct::getProductId)
                .collect(Collectors.toList());
    }

    public long getFollowingProductCount(String customerId) {
        return this.followProductRepository.count(Example.of(new FollowProduct(customerId)));
    }

    @Transactional
    public void followStore(FollowStore followStore) {
        this.followStoreRepository.save(followStore);
    }

    @Transactional
    public void unfollowStore(FollowStore followStore) {
        this.followStoreRepository.findOne(Example.of(followStore))
                .ifPresent(this.followStoreRepository::delete);
    }

    public boolean isFollowingStore(FollowStore followStore) {
        return this.followStoreRepository.count(Example.of(followStore)) > 0;
    }

    public List<String> getFollowStores(String customerId) {
        return this.followStoreRepository
                .findAll(Example.of(new FollowStore(customerId)))
                .stream()
                .map(FollowStore::getStoreId)
                .collect(Collectors.toList());
    }

    public long getFollowingStoreCount(String customerId) {
        return this.followStoreRepository.count(Example.of(new FollowStore(customerId)));
    }

}
