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

package com.github.shop.catalog;

import java.util.List;

/**
 * Product Service.
 *
 * @author Zhi Tang
 * @since 1.0
 */
public interface ProductService {

    /**
     * Create product category.
     *
     * @param category category
     */
    void createProductCategory(ProductCategory category);

    /**
     * Create product categories.
     *
     * @param categories categories
     */
    void createProductCategories(List<ProductCategory> categories);

    void deleteProductCategory(long id);

    void updateProductCategory(ProductCategory category);

    List<ProductCategory> getTopProductCategories();

    List<ProductCategory> getProductCategories(long parentId);

    ProductCategory getProductCategory(long id);

    Product getProduct(long id);

    void createProduct(Product product);

    List<FirstProduct> getProducts(ProductQuery query);

}
