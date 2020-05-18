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

package com.mallfoundry.customer;

import com.mallfoundry.data.SliceList;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternalBrowsingProductService implements BrowsingProductService {

    private final BrowsingProductRepository browsingProductRepository;

    public InternalBrowsingProductService(BrowsingProductRepository browsingProductRepository) {
        this.browsingProductRepository = browsingProductRepository;
    }

    @Override
    public BrowsingProduct createBrowsingProduct(String customerId, String productId) {
        return null;
    }

    public void addBrowsingProduct(BrowsingProduct browsingProduct) {
        this.browsingProductRepository.save(InternalBrowsingProduct.of(browsingProduct));
    }

    @Override
    public void deleteBrowsingProduct(String id) {

    }

    @Override
    public void deleteBrowsingProducts(List<String> ids) {

    }

    @Override
    public SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query) {
        return null;
    }

    @Override
    public long getBrowsingProductCount(BrowsingProductQuery query) {
        return 0;
    }

    public long getBrowsingProductCount(String customerId) {
        return this.browsingProductRepository.count(Example.of(new InternalBrowsingProduct(customerId)));
    }

}
