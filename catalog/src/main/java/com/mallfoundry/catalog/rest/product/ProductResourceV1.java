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

package com.mallfoundry.catalog.rest.product;

import com.mallfoundry.catalog.application.ProductService;
import com.mallfoundry.catalog.domain.product.ProductQuery;
import com.mallfoundry.storefront.domain.product.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("catalogProductResourceV1")
@RequestMapping("/v1/catalog")
public class ProductResourceV1 {

    private final ProductService productService;

    public ProductResourceV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/search")
    public List<Product> search(ProductQuery search) {
        return this.productService.search(search);
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") String id) {
        return this.productService.getProduct(id);
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(value = "ids") List<String> ids) {
        return this.productService.getProducts(ids);
    }
}
