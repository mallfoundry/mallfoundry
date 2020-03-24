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

import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductService;
import com.mallfoundry.store.product.search.ProductQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class ProductResourceV1 {

    private final ProductService productService;

    public ProductResourceV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public SliceList<Product> searchProduct(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "store_id", required = false) String storeId,
            @RequestParam(name = "product_id", required = false) String productId) {
        return this.productService.getProducts(ProductQuery.builder()
                .page(page).limit(limit)
                .productId(productId).storeId(storeId).build());
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Long id) {
        return this.productService.getProduct(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        Product newProduct = this.productService.newProduct();
        BeanUtils.copyProperties(product, newProduct);
        return this.productService.saveProduct(newProduct);
    }

    @PutMapping("/products/{id}")
    public void saveProduct(@PathVariable("id") Long id,
                            @RequestBody Product newProduct) {
        newProduct.setId(id);
        this.productService.saveProduct(newProduct);
    }
}
