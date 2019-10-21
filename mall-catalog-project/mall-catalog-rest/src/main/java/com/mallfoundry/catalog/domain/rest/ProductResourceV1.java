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

package com.mallfoundry.catalog.domain.rest;

import com.mallfoundry.catalog.application.product.ProductService;
import com.mallfoundry.catalog.domain.product.Product;
import com.mallfoundry.catalog.domain.search.ProductSearchQuery;
import com.mallfoundry.catalog.domain.product.ProductSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/catalog")
public class ProductResourceV1 {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

    public ProductResourceV1(ProductService productService, ProductSearchService productSearchService) {
        this.productService = productService;
        this.productSearchService = productSearchService;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") String id) {
        return this.productService.getProduct(id);
    }

    @GetMapping("/products/search")
    public List<Product> search(ProductSearchQuery search) {
        return this.productSearchService.search(search);
    }

    @PostMapping("/products")
    public String createProduct(@RequestBody Product product) {
        this.productService.addProduct(product);
        return "添加成功";
    }
}
