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

package com.mallfoundry.store.infrastructure.persistence.mybatis;

import com.mallfoundry.store.domain.StoreInfo;
import com.mallfoundry.store.domain.StoreRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepositoryMybatis extends StoreRepositorySupport {

    private final StoreMapper storeMapper;

    public StoreRepositoryMybatis(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
    }

    @Override
    protected StoreInfo doFindById(String id) {
        return this.storeMapper.selectById(id);
    }

    @Override
    protected void doAdd(StoreInfo store) {
        this.storeMapper.insert(store);
    }

    @Override
    protected void doUpdate(StoreInfo store) {
        this.storeMapper.update(store);
    }
}
