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

import org.mallfoundry.catalog.product.search.ProductSearcher;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.inventory.InventoryAdjustment;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalProductService implements ProductService {

    private static final String PRODUCT_ID_VALUE_NAME = "catalog.product.id";

    private final ProductRepository productRepository;

    private final ProductSearcher productSearcher;

    private final ApplicationEventPublisher eventPublisher;


    public InternalProductService(ProductRepository productRepository,
                                  ProductSearcher productSearcher,
                                  ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProductQuery createProductQuery() {
        return new InternalProductQuery();
    }

    @Override
    public ProductId createProductId(String id) {
        return new InternalProductId(id);
    }

    @Override
    public Product createProduct(String id) {
        return new InternalProduct(id);
    }

    @Transactional
    @Override
    public Product addProduct(Product product) {
        if (Objects.isNull(product.getId())) {
            product.setId(PrimaryKeyHolder.next(PRODUCT_ID_VALUE_NAME));
        }
        var addedProduct = this.productRepository.save(InternalProduct.of(product));
        this.eventPublisher.publishEvent(new InternalProductAddedEvent(addedProduct));
        return addedProduct;
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        var newProduct = InternalProduct.of(product);
        var oldProduct = this.productRepository.findById(newProduct.getId()).orElseThrow();
        BeanUtils.copyProperties(newProduct, oldProduct, "variants");
        var savedProduct = this.productRepository.save(oldProduct);
        this.eventPublisher.publishEvent(new InternalProductChangedEvent(savedProduct));
    }

    @Transactional
    @Override
    public void adjustInventory(InventoryAdjustment adjustment) {
        var product = this.productRepository.findById(adjustment.getProductId()).orElseThrow();
        product.adjustVariantInventoryQuantity(adjustment.getVariantId(), adjustment.getQuantityDelta());
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
        return this.productSearcher.search(query);
    }


}
