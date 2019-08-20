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
import com.github.shop.catalog.Product;
import com.github.shop.catalog.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryMybatis implements ProductRepository {

    private final ProductMapper productMapper;

    private final PrimaryKeyGenerator<Long> primaryKeyGenerator;

    public ProductRepositoryMybatis(ProductMapper productMapper, PrimaryKeyGenerator<Long> primaryKeyGenerator) {
        this.productMapper = productMapper;
        this.primaryKeyGenerator = primaryKeyGenerator;
    }

    @Override
    public Product findById(long id) {
        return productMapper.selectProductById(id);
    }

    @Override
    public void create(Product product) {
        // set new id
        product.setId(this.nextProductId());
        productMapper.insertProduct(product);
        product.getItems().
                forEach(item -> {
                    item.setId(nextProductId());
                    item.setProductId(product.getId());
                });
        productMapper.insertProductItems(product);
    }

    private Long nextProductId() {
        return 10000000000000L + primaryKeyGenerator.nextVal("product.id");
    }
}
