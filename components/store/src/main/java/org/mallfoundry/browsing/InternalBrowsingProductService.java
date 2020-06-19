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

package org.mallfoundry.browsing;

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class InternalBrowsingProductService implements BrowsingProductService {

    private final ProductService productService;

    private final BrowsingProductRepository browsingProductRepository;

    public InternalBrowsingProductService(ProductService productService,
                                          BrowsingProductRepository browsingProductRepository) {
        this.productService = productService;
        this.browsingProductRepository = browsingProductRepository;
    }

    @Override
    public BrowsingProduct createBrowsingProduct(String browserId, String productId) {
        return new InternalBrowsingProduct(browserId, productId);
    }

    @Override
    public BrowsingProductQuery createBrowsingProductQuery() {
        return new InternalBrowsingProductQuery();
    }

    private BrowsingProduct hitBrowsingProduct(BrowsingProduct browsingProduct) {
        var product = this.productService.getProduct(browsingProduct.getId()).orElseThrow();
        return browsingProduct.toBuilder().price(product.getPrice())
                .name(product.getName())
                .imageUrl(CollectionUtils.firstElement(product.getImageUrls()))
                .hit()
                .build();
    }

    @Transactional
    @Override
    public BrowsingProduct addBrowsingProduct(String browserId, String productId) {
        var browsingProduct = this.hitBrowsingProduct(
                this.browsingProductRepository.findByIdAndBrowserId(productId, browserId)
                        .orElseGet(() -> this.createBrowsingProduct(browserId, productId)));
        return this.browsingProductRepository.save(browsingProduct);
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
