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

package com.mallfoundry.catalog.infrastructure.persistence.mybatis.product;

import com.mallfoundry.catalog.domain.product.Product;
import com.mallfoundry.catalog.domain.product.ProductRepository;
import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryMybatis implements ProductRepository {

    private final ProductMapper productMapper;

    public ProductRepositoryMybatis(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Product findById(String id) {
        return productMapper.selectProductById(id);
    }

    @Override
    public void add(Product product) {
        // set new id
        product.setId(this.nextProductId());
        productMapper.insertProduct(product);
        product.getVariants().
                forEach(variant -> {
                    variant.setId(this.nextProductVariantId());
                    variant.setProductId(product.getId());
                });
        productMapper.insertProductVariants(product);
    }

    private String nextProductId() {
        long nextProductId = PrimaryKeyHolder.sequence().nextVal("product.id");
        return String.valueOf(10000000000000L + nextProductId);
    }

    private String nextProductVariantId() {
        long nextVariantId = PrimaryKeyHolder.sequence().nextVal("product.variant.id");
        return String.valueOf(10000000000000L + nextVariantId);
    }
}
