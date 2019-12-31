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

package com.mallfoundry.store.rest;

import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductService;
import com.mallfoundry.store.product.search.ProductQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class ProductResourceV1 {

    private final ProductService productService;

    public ProductResourceV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> searchProduct(
            ProductQuery query,
            @RequestParam(name = "store_id", required = false) String storeId) {

        if (Objects.nonNull(storeId)) {
            query.setStoreId(storeId);
        }

        return this.productService.search(query);
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Long id) {
        return this.productService.getProduct(id);
    }
}
