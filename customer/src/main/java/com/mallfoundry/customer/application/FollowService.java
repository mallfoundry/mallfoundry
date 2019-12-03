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

package com.mallfoundry.customer.application;

import com.mallfoundry.customer.domain.follow.FollowProduct;
import com.mallfoundry.customer.domain.follow.FollowProductRepository;
import com.mallfoundry.customer.domain.follow.FollowStore;
import com.mallfoundry.customer.domain.follow.FollowStoreRepository;
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
        this.followProductRepository.add(followProduct);
    }

    @Transactional
    public void unfollowProduct(FollowProduct followProduct) {
        this.followProductRepository.delete(followProduct);
    }

    public boolean isFollowingProduct(FollowProduct followProduct) {
        return this.followProductRepository.exist(followProduct);
    }

    public List<String> getFollowingProducts(String customerId) {
        return this.followProductRepository
                .findListByCustomerId(customerId)
                .stream()
                .map(FollowProduct::getProductId)
                .collect(Collectors.toList());
    }

    public int getFollowingProductCount(String customerId) {
        return this.followProductRepository.countByCustomerId(customerId);
    }

    @Transactional
    public void followStore(FollowStore followStore) {
        this.followStoreRepository.add(followStore);
    }

    @Transactional
    public void unfollowStore(FollowStore followStore) {
        this.followStoreRepository.delete(followStore);
    }

    public boolean isFollowingStore(FollowStore followStore) {
        return this.followStoreRepository.exist(followStore);
    }


    public List<String> getFollowStores(String customerId) {
        return this.followStoreRepository
                .findListByCustomerId(customerId)
                .stream()
                .map(FollowStore::getStoreId)
                .collect(Collectors.toList());
    }

    public int getFollowingStoreCount(String customerId) {
        return this.followStoreRepository.countByCustomerId(customerId);
    }

}
