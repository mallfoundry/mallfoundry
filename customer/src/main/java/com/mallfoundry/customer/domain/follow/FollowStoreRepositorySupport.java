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

package com.mallfoundry.customer.domain.follow;

import java.util.List;

public abstract class FollowStoreRepositorySupport implements FollowStoreRepository {

    protected abstract void doAdd(FollowStore followStore);

    protected abstract boolean doExist(FollowStore followStore);

    protected abstract void doDelete(FollowStore followStore);

    protected abstract List<FollowStore> doFindListByCustomerId(String customerId);

    @Override
    public void add(FollowStore followStore) {
        if (!this.exist(followStore)) {
            this.doAdd(followStore);
        }
    }

    @Override
    public void delete(FollowStore followStore) {
        this.doDelete(followStore);
    }

    @Override
    public boolean exist(FollowStore followStore) {
        return this.doExist(followStore);
    }

    @Override
    public List<FollowStore> findListByCustomerId(String customerId) {
        return this.doFindListByCustomerId(customerId);
    }
}
