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

package com.github.shop.catalog.infrastructure.persistence.mybatis;

import com.github.shop.keygen.PrimaryKeyGenerator;
import com.github.shop.catalog.ProductCategory;
import com.github.shop.catalog.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryRepositoryMybatis implements ProductCategoryRepository {

    private final ProductCategoryMapper productCategoryMapper;

    private final PrimaryKeyGenerator<Long> primaryKeyGenerator;

    @Autowired(required = false)
    public ProductCategoryRepositoryMybatis(ProductCategoryMapper productCategoryMapper,
                                            PrimaryKeyGenerator<Long> primaryKeyGenerator) {
        this.productCategoryMapper = productCategoryMapper;
        this.primaryKeyGenerator = primaryKeyGenerator;
    }

    @Override
    public List<ProductCategory> findTopList() {
        return this.productCategoryMapper.selectListByLevel(ProductCategory.TOP_LEVEL);
    }

    @Override
    public void create(ProductCategory category) {
        category.setId(this.nextProductCategoryId());
        this.productCategoryMapper.insertCategory(category);
    }

    @Override
    public void create(List<ProductCategory> categories) {
        categories.forEach(productCategory -> productCategory.setId(this.nextProductCategoryId()));
        this.productCategoryMapper.insertCategories(categories);
    }

    private int nextProductCategoryId() {
        return 10000000 + primaryKeyGenerator.nextVal("product.category.id").intValue();
    }
}
