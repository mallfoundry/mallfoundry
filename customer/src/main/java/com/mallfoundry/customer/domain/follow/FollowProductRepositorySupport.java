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

public abstract class FollowProductRepositorySupport implements FollowProductRepository {

    protected abstract void doAdd(FollowProduct followProduct);

    protected abstract void doDelete(FollowProduct followProduct);

    protected abstract boolean doExist(FollowProduct followProduct);

    protected abstract List<FollowProduct> doFindListByCustomerId(String customerId);

    @Override
    public void add(FollowProduct followProduct) {
        if (!this.exist(followProduct)) {
            this.doAdd(followProduct);
        }
    }

    @Override
    public void delete(FollowProduct followProduct) {
        this.doDelete(followProduct);
    }

    @Override
    public boolean exist(FollowProduct followProduct) {
        return this.doExist(followProduct);
    }

    @Override
    public List<FollowProduct> findListByCustomerId(String customerId) {
        return this.doFindListByCustomerId(customerId);
    }
}
