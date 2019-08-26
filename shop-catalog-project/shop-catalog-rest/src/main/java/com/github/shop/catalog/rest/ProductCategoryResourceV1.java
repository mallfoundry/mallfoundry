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

package com.github.shop.catalog.rest;


import com.github.shop.catalog.ProductCategory;
import com.github.shop.catalog.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class ProductCategoryResourceV1 {

    private final ProductService productService;

    public ProductCategoryResourceV1(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createCategory(@RequestBody ProductCategory category) {
        this.productService.createProductCategory(category);
        return ResponseEntity.ok("添加成功!");
    }

    @PutMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateCategory(@RequestBody ProductCategory category) {
        this.productService.updateProductCategory(category);
        return ResponseEntity.ok("修改成功!");
    }


    @GetMapping("/categories")
    public List<ProductCategory> getCategories(@RequestParam(required = false, name = "parent_id") Long parentId) {
        return Objects.isNull(parentId) ?
                this.productService.getTopProductCategories() :
                this.productService.getProductCategories(parentId);
    }

    @GetMapping("/categories/{id}")
    public ProductCategory getCategory(@PathVariable("id") long id) {
        return this.productService.getProductCategory(id);
    }
}
