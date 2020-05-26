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

package org.mallfoundry.customer;

import org.mallfoundry.browsing.BrowsingProduct;
import org.mallfoundry.browsing.BrowsingProductQuery;
import org.mallfoundry.browsing.BrowsingProductService;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.security.SecurityUserHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InternalBrowsingProductService implements BrowsingProductService {

    private static final String BROWSING_PRODUCT_ID_VALUE_NAME = "browsing.product.id";

    private final BrowsingProductRepository browsingProductRepository;

    public InternalBrowsingProductService(BrowsingProductRepository browsingProductRepository) {
        this.browsingProductRepository = browsingProductRepository;
    }

    @Override
    public BrowsingProduct createBrowsingProduct() {
        var browserId = SecurityUserHolder.getUserId();
        return new InternalBrowsingProduct(PrimaryKeyHolder.next(BROWSING_PRODUCT_ID_VALUE_NAME), browserId);
    }

    @Override
    public BrowsingProductQuery createBrowsingProductQuery() {
        return new InternalBrowsingProductQuery();
    }

    @Transactional
    public BrowsingProduct addBrowsingProduct(BrowsingProduct browsingProduct) {
        return this.browsingProductRepository.save(InternalBrowsingProduct.of(browsingProduct));
    }

    @Transactional
    @Override
    public void deleteBrowsingProduct(String id) {
        var browsingProduct = this.browsingProductRepository.findById(id).orElseThrow();
        this.browsingProductRepository.delete(browsingProduct);
    }

    @Transactional
    @Override
    public void deleteBrowsingProducts(List<String> ids) {
        var browsingProducts = this.browsingProductRepository.findAllById(ids);
        this.browsingProductRepository.deleteAll(browsingProducts);
    }

    @Override
    public SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query) {
        return CastUtils.cast(this.browsingProductRepository.findAll(query));
    }

    @Override
    public long getBrowsingProductCount(BrowsingProductQuery query) {
        return this.browsingProductRepository.count(query);
    }
}
