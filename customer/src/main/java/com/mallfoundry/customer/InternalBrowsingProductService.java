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

import com.mallfoundry.browsing.BrowsingProduct;
import com.mallfoundry.browsing.BrowsingProductQuery;
import com.mallfoundry.browsing.BrowsingProductService;
import com.mallfoundry.data.SliceList;
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

    @Override
    public BrowsingProductQuery createBrowsingProductQuery() {
        return null;
    }

    public BrowsingProduct saveBrowsingProduct(BrowsingProduct browsingProduct) {
        return this.browsingProductRepository.save(InternalBrowsingProduct.of(browsingProduct));
    }

    @Override
    public void deleteBrowsingProduct(String id) {
//        this.browsingProductRepository.
//        this.browsingProductRepository.delete();
    }

    @Override
    public void deleteBrowsingProducts(List<String> ids) {

    }

    @Override
    public SliceList<BrowsingProduct> getBrowsingProducts(com.mallfoundry.browsing.BrowsingProductQuery query) {
        return null;
    }

    @Override
    public long getBrowsingProductCount(BrowsingProductQuery query) {
        return 0;
    }
}
