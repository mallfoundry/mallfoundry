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

import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InternalBrowsingProductService implements BrowsingProductService {

    private final BrowsingProductRepository browsingProductRepository;

    public InternalBrowsingProductService(BrowsingProductRepository browsingProductRepository) {
        this.browsingProductRepository = browsingProductRepository;
    }

    @Override
    public BrowsingProduct createBrowsingProduct(String id) {
        return new InternalBrowsingProduct(id);
    }

    @Override
    public BrowsingProductQuery createBrowsingProductQuery() {
//        var query = new InternalBrowsingProductQuery();
//        query.setBrowserId(SecurityUserHolder.getUserId());
//        return query;
        return new InternalBrowsingProductQuery();
    }

    @Transactional
    @Override
    public BrowsingProduct addBrowsingProduct(BrowsingProduct browsingProduct) {
        return this.browsingProductRepository.save(InternalBrowsingProduct.of(browsingProduct));
    }

    @Override
    public SliceList<BrowsingProduct> getBrowsingProducts(BrowsingProductQuery query) {
        return CastUtils.cast(this.browsingProductRepository.findAll(query));
    }

    @Override
    public long getBrowsingProductCount(BrowsingProductQuery query) {
        return this.browsingProductRepository.count(query);
    }

    @Transactional
    @Override
    public void deleteBrowsingProduct(String browserId, String id) {
        var browsingProduct = this.browsingProductRepository.findByIdAndBrowserId(id, browserId).orElseThrow();
        this.browsingProductRepository.delete(browsingProduct);
    }

    @Transactional
    @Override
    public void deleteBrowsingProducts(String browserId, List<String> ids) {
        var browsingProducts = this.browsingProductRepository.findAllByIdInAndBrowserId(ids, browserId);
        this.browsingProductRepository.deleteAll(browsingProducts);
    }

}
