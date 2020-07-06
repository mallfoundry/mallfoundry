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

package org.mallfoundry.catalog.product;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class DefaultProductService implements ProductService {

    private final ProductProcessorsInvoker processorsInvoker;

    private final ProductRepository productRepository;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultProductService(ProductProcessorsInvoker processorsInvoker,
                                 ProductRepository productRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.processorsInvoker = processorsInvoker;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProductQuery createProductQuery() {
        return new DefaultProductQuery();
    }

    @Override
    public ProductId createProductId(String id) {
        return new ImmutableProductId(id);
    }

    @Override
    public Product createProduct(String id) {
        return this.productRepository.create(id);
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        var addedProduct = this.productRepository.save(
                this.processorsInvoker.invokeProcessPreAddProduct(product));
        this.eventPublisher.publishEvent(new ImmutableProductAddedEvent(addedProduct));
        return addedProduct;
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        var oldProduct = this.productRepository.findById(product.getId()).orElseThrow();
        BeanUtils.copyProperties(product, oldProduct, "variants");
        var savedProduct = this.productRepository.save(oldProduct);
        this.eventPublisher.publishEvent(new ImmutableProductChangedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void adjustInventory(InventoryAdjustment adjustment) {
        var product = this.productRepository.findById(adjustment.getProductId()).orElseThrow();
        product.adjustInventoryQuantity(adjustment.getVariantId(), adjustment.getQuantityDelta());
    }

    @Transactional
    @Override
    public void adjustInventories(List<InventoryAdjustment> adjustments) {
        adjustments.forEach(this::adjustInventory);
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(String id) {
        return CastUtils.cast(this.productRepository.findById(id));
    }

    @Override
    public SliceList<Product> getProducts(ProductQuery query) {
        return this.productRepository.findAll(query);
    }


}
