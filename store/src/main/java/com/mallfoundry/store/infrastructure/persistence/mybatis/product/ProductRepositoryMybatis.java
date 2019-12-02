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

package com.mallfoundry.store.infrastructure.persistence.mybatis.product;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.store.domain.product.Product;
import com.mallfoundry.store.domain.product.ProductRepositorySupport;
import com.mallfoundry.store.domain.product.ProductVariant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryMybatis extends ProductRepositorySupport {

    private final ProductMapper productMapper;

    private final ProductVariantMapper productVariantMapper;

    public ProductRepositoryMybatis(ProductMapper productMapper,
                                    ProductVariantMapper productVariantMapper) {
        this.productMapper = productMapper;
        this.productVariantMapper = productVariantMapper;
    }

    @Override
    public void doAdd(Product product) {
        // set new id
        product.setId(this.nextProductId());
        this.productMapper.insert(product);
        List<ProductVariant> variants = product.getVariants();
        variants.forEach(variant -> {
            variant.setId(this.nextProductVariantId());
            variant.setProductId(product.getId());
        });

        this.productVariantMapper.insertList(variants);
    }

    @Override
    protected void doDelete(String id) {
        this.productMapper.deleteById(id);
        this.productVariantMapper.deleteByProductId(id);
    }

    @Override
    protected void doUpdate(Product product) {

    }

    @Override
    public Product doFindById(String id) {
        return this.productMapper.selectById(id);
    }

    private String nextProductId() {
        long nextProductId = PrimaryKeyHolder.sequence().nextVal("store.product.id");
        return String.valueOf(10000000000000L + nextProductId);
    }

    private String nextProductVariantId() {
        long nextVariantId = PrimaryKeyHolder.sequence().nextVal("store.product.variant.id");
        return String.valueOf(10000000000000L + nextVariantId);
    }
}
