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

import com.mallfoundry.customer.domain.BrowsingProduct;
import com.mallfoundry.customer.domain.BrowsingProductQuery;
import com.mallfoundry.customer.domain.BrowsingProductRepository;
import com.mallfoundry.data.OffsetList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowsingProductService {

    private final BrowsingProductRepository browsingProductRepository;

    public BrowsingProductService(BrowsingProductRepository browsingProductRepository) {
        this.browsingProductRepository = browsingProductRepository;
    }

    public void addBrowsingProduct(BrowsingProduct browsingProduct) {
        if (this.browsingProductRepository.exists(browsingProduct)) {
            browsingProduct.setNowBrowsingTime();
            this.browsingProductRepository.update(browsingProduct);
        } else {
            this.browsingProductRepository.add(browsingProduct);
        }
    }

    public void deleteBrowsingProduct(BrowsingProduct browsingProduct) {
        this.browsingProductRepository.delete(browsingProduct);
    }

    public void deleteBrowsingProducts(List<BrowsingProduct> browsingProducts) {
        this.browsingProductRepository.delete(browsingProducts);
    }

    public OffsetList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query) {
        return this.browsingProductRepository.findListByQuery(query);
    }

    public int getBrowsingProductCount(String customerId) {
        return this.browsingProductRepository.countByCustomerId(customerId);
    }

}
