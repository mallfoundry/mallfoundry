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
import com.mallfoundry.store.domain.product.CustomCollection;
import com.mallfoundry.store.domain.product.CustomCollectionRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCollectionRepositoryMybatis extends CustomCollectionRepositorySupport {

    private final CustomCollectionMapper customCollectionMapper;

    public CustomCollectionRepositoryMybatis(CustomCollectionMapper customCollectionMapper) {
        this.customCollectionMapper = customCollectionMapper;
    }

    @Override
    public void doAdd(CustomCollection collection) {
        collection.setId(this.nextCollectionId());
        this.customCollectionMapper.insert(collection);
    }

    @Override
    public void doDelete(String id) {

        List<String> deleteIds = new ArrayList<>();
        deleteIds.add(id);
        List<String> childrenIds = this.customCollectionMapper.selectIdsByParentIds(List.of(id));
        deleteIds.addAll(childrenIds);
        this.customCollectionMapper.deleteByIds(deleteIds);
    }

    @Override
    public void doUpdate(CustomCollection collection) {
        this.customCollectionMapper.update(collection);
    }

    @Override
    protected List<CustomCollection> doFindTopList(String storeId) {
        return this.customCollectionMapper.selectListByParentId(storeId, CustomCollection.ROOT_ID);
    }

    @Override
    protected CustomCollection doFindById(String id) {
        return this.customCollectionMapper.selectById(id);
    }

    @Override
    protected List<CustomCollection> doFindListByParentId(String parentId) {
        return this.customCollectionMapper.selectListByParentId(null, parentId);
    }

    private String nextCollectionId() {
        long nextId = PrimaryKeyHolder.sequence().nextVal("storefront.custom_collection.id");
        return String.valueOf(10000000000000L + nextId);
    }
}
