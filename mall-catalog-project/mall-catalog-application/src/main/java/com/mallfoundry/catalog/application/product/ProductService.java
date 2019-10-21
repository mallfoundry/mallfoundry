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

package com.mallfoundry.catalog.application.product;

import com.mallfoundry.catalog.domain.product.Product;
import com.mallfoundry.catalog.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private com.mallfoundry.catalog.domain.product.ProductService productService;

    public ProductService(ProductRepository productRepository,
                          com.mallfoundry.catalog.domain.product.ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public Product getProduct(String id) {
        return this.productRepository.findById(id);
    }

    @Transactional
    public void addProduct(Product product) {
        this.productService.add(product);
    }
}
