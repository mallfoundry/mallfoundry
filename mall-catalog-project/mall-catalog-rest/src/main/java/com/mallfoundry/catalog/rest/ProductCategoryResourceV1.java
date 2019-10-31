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

package com.mallfoundry.catalog.rest;


import com.mallfoundry.catalog.application.category.ProductCategoryService;
import com.mallfoundry.catalog.domain.category.ProductCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * The product category restful API.
 */
@RestController
@RequestMapping("/v1/catalog")
public class ProductCategoryResourceV1 {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryResourceV1(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    /**
     * Returns a new object created if the validation passes and the creation succeeds.
     *
     * @param category the category a new object.
     * @return a new object after successful creation.
     */
    @PostMapping(value = "/categories")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategory category) {
        try {
            this.productCategoryService.addProductCategory(category);
            return ResponseEntity.ok(this.productCategoryService.getProductCategory(category.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id) {
        try {
            this.productCategoryService.deleteProductCategory(id);
            return ResponseEntity.ok("删除商品分类成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping(value = "/categories")
    public ResponseEntity<String> updateCategory(@RequestBody ProductCategory category) {
        this.productCategoryService.updateProductCategory(category);
        return ResponseEntity.ok("修改成功!");
    }

    @GetMapping("/categories")
    public List<ProductCategory> getCategories(@RequestParam(required = false, name = "parent_id") Long parentId) {
        return Objects.isNull(parentId) ?
                this.productCategoryService.getTopProductCategories() :
                this.productCategoryService.getProductCategories(parentId);
    }

    @GetMapping("/categories/{id}")
    public ProductCategory getCategory(@PathVariable("id") long id) {
        return this.productCategoryService.getProductCategory(id);
    }
}
