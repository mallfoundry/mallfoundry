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

package com.mallfoundry.store.domain;

import java.util.List;

public abstract class CustomCollectionRepositorySupport implements CustomCollectionRepository {

    protected abstract void doAdd(CustomCollection collection);

    protected abstract void doDelete(String id);

    protected abstract void doUpdate(CustomCollection collection);

    protected abstract List<CustomCollection> doFindTopList(String storeId);

    protected abstract List<CustomCollection> doFindListByParentId(String parentId);

    protected abstract CustomCollection doFindById(String id);

    @Override
    public void add(CustomCollection collection) {
        this.doAdd(collection);
    }

    @Override
    public void delete(String id) {
        this.doDelete(id);
    }

    @Override
    public void update(CustomCollection collection) {
        this.doUpdate(collection);
    }

    @Override
    public List<CustomCollection> findTopList(String storeId) {
        return this.doFindTopList(storeId);
    }

    @Override
    public CustomCollection findById(String id) {
        return this.doFindById(id);
    }

    @Override
    public List<CustomCollection> findListByParentId(String parentId) {
        return this.doFindListByParentId(parentId);
    }
}
