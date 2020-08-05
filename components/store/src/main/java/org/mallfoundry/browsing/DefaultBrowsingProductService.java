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

package org.mallfoundry.browsing;

import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DefaultBrowsingProductService implements BrowsingProductService {

    private final ProductService productService;

    private final BrowsingProductRepository browsingProductRepository;

    public DefaultBrowsingProductService(ProductService productService,
                                         BrowsingProductRepository browsingProductRepository) {
        this.productService = productService;
        this.browsingProductRepository = browsingProductRepository;
    }

    private BrowsingProduct createBrowsingProduct(String browserId, String productId) {
        return this.browsingProductRepository.create(browserId, productId);
    }

    @Override
    public BrowsingProductQuery createBrowsingProductQuery() {
        return new DefaultBrowsingProductQuery();
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
    public BrowsingProduct hitBrowsingProduct(String browserId, String productId) {
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
    public long countBrowsingProducts(BrowsingProductQuery query) {
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
