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

package com.mallfoundry.storefront.infrastructure.persistence.mybatis.product;

import com.mallfoundry.storefront.domain.product.CustomCollection;
import com.mallfoundry.storefront.domain.product.CustomCollectionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCollectionRepositoryMybatis implements CustomCollectionRepository {

    private final CustomCollectionMapper productCollectionMapper;

    public CustomCollectionRepositoryMybatis(CustomCollectionMapper productCollectionMapper) {
        this.productCollectionMapper = productCollectionMapper;
    }

    @Override
    public void add(CustomCollection collection) {
        this.productCollectionMapper.insertProductCollection(collection);
    }

    @Override
    public void delete(CustomCollection collection) {

        List<String> deleteIds = new ArrayList<>();
        deleteIds.add(collection.getId());

        List<String> childrenIds = this.productCollectionMapper.selectIdsByParentIds(List.of(collection.getId()));


        this.productCollectionMapper.deleteByIds(deleteIds);
    }

    @Override
    public void update(CustomCollection collection) {

    }

    @Override
    public List<CustomCollection> findTopCollections() {
        return null;
    }

    @Override
    public CustomCollection findById(String id) {
        return null;
    }
}
