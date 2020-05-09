/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.store.product.rest;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.store.product.Product;
import com.mallfoundry.store.product.ProductService;
import com.mallfoundry.store.product.ProductType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
public class ProductResourceV1 {

    private final ProductService productService;

    public ProductResourceV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public SliceList<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                          @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                          @RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "store_id", required = false) String storeId,
                                          @RequestParam(name = "collection_ids", required = false) Set<String> collectionIds,
                                          @RequestParam(name = "types", required = false) Set<String> types) {
        return this.productService.getProducts(this.productService.createProductQuery().toBuilder()
                .page(page).limit(limit).name(name).storeId(storeId)
                .types(() -> Stream.ofNullable(types).flatMap(Set::stream).filter(StringUtils::isNotEmpty)
                        .map(StringUtils::upperCase).map(ProductType::valueOf).collect(Collectors.toSet()))
                .collectionIds(collectionIds).build());
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProduct(@PathVariable("id") String id) {
        return this.productService.getProduct(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody ProductRequest request) {
        var newProduct = this.productService.createProduct();
        return this.productService.saveProduct(newProduct);
    }

    @PutMapping("/products/{id}")
    public void saveProduct(@PathVariable("id") String id,
                            @RequestBody ProductRequest request) {
        var product = this.productService.getProduct(id).orElseThrow();
        this.productService.saveProduct(product);
    }
}
