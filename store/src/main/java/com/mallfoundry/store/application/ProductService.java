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

package com.mallfoundry.store.application;

import com.mallfoundry.store.domain.product.Product;
import com.mallfoundry.store.domain.product.search.ProductQuery;
import com.mallfoundry.store.domain.product.ProductRepository;
import com.mallfoundry.store.domain.product.search.ProductSearcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductSearcher productSearcher;

    public ProductService(ProductRepository productRepository,
                          ProductSearcher productSearcher) {
        this.productRepository = productRepository;
        this.productSearcher = productSearcher;
    }

    @Transactional
    public void addProduct(Product product) {
        this.productRepository.add(product);
    }

    public Product getProduct(String id) {
        return this.productRepository.findById(id);
    }

    public List<Product> search(ProductQuery query) {
        return this.productSearcher.search(query);
    }
}
