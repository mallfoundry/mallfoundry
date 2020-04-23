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

package com.mallfoundry.store.product;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.product.search.ProductQuery;
import com.mallfoundry.store.product.search.ProductSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductSearcher productSearcher;

    private ApplicationEventPublisher eventPublisher;

    public ProductService(ProductRepository productRepository,
                          ProductSearcher productSearcher,
                          ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
        this.eventPublisher = eventPublisher;
    }

    public InternalProduct newProduct() {
        return new InternalProduct();
    }

    @Transactional
    public InternalProduct saveProduct(InternalProduct newProduct) {
        InternalProduct savedProduct;
        if (Objects.isNull(newProduct.getId())) {
            newProduct.create();
            savedProduct = this.productRepository.save(newProduct);
        } else {
            InternalProduct oldProduct = this.getProduct(newProduct.getId()).orElseThrow();
            BeanUtils.copyProperties(newProduct, oldProduct, "variants");
            oldProduct.getVariants().clear();
            oldProduct.getVariants().addAll(newProduct.getVariants());
            savedProduct = this.productRepository.save(oldProduct);
        }
        this.eventPublisher.publishEvent(new ProductSavedEvent(savedProduct));
        return savedProduct;
    }

    @Transactional
    public Optional<InternalProduct> getProduct(Long id) {
        return this.productRepository.findById(id);
    }

    public Optional<InternalProduct> getProduct(String id) {
        return this.productRepository.findById(Long.parseLong(id));
    }

    public SliceList<InternalProduct> getProducts(ProductQuery query) {
        return this.productSearcher.search(query);
    }
}
