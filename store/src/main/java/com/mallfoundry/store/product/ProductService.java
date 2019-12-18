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

import com.mallfoundry.store.product.search.ProductQuery;
import com.mallfoundry.store.product.search.ProductSearcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public void addProduct(Product product) {
        this.eventPublisher.publishEvent(new ProductAddedEvent(this.productRepository.save(product)));
    }

    @Transactional
    public Product createProduct() {
        return this.productRepository.save(new Product());
    }

    public Optional<Product> getProduct(Integer id) {
        return this.productRepository.findById(id);
    }

    public List<Product> search(ProductQuery query) {
        return this.productSearcher.search(query);
    }
}
