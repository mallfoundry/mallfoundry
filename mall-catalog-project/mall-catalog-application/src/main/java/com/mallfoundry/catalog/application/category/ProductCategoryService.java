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

package com.mallfoundry.catalog.application.category;

import com.mallfoundry.catalog.domain.category.ProductCategory;
import com.mallfoundry.catalog.domain.category.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public void addProductCategory(ProductCategory category) {
        this.productCategoryRepository.add(category);
    }

    public ProductCategory getProductCategory(long id) {
        return this.productCategoryRepository.findById(id);
    }

    public void updateProductCategory(ProductCategory category) {
        this.productCategoryRepository.update(category);
    }

    public List<ProductCategory> getTopProductCategories() {
        return this.productCategoryRepository.findTopList();
    }

    public List<ProductCategory> getProductCategories(long parentId) {
        return this.productCategoryRepository.findListByParentId(parentId);
    }

    public void deleteProductCategory(long parentId) {
        this.productCategoryRepository.deleteById(parentId);
    }
}
