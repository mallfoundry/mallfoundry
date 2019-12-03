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

package com.mallfoundry.customer.infrastructure.persistence.mybatis.follow;

import com.mallfoundry.customer.domain.follow.FollowStore;
import com.mallfoundry.customer.domain.follow.FollowStoreRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowStoreRepositoryMybatis extends FollowStoreRepositorySupport {

    private final FollowStoreMapper followStoreMapper;

    public FollowStoreRepositoryMybatis(FollowStoreMapper followStoreMapper) {
        this.followStoreMapper = followStoreMapper;
    }

    @Override
    protected void doAdd(FollowStore followStore) {
        this.followStoreMapper.insert(followStore);
    }

    @Override
    protected void doDelete(FollowStore followStore) {
        this.followStoreMapper.delete(followStore);
    }

    @Override
    protected boolean doExist(FollowStore followStore) {
        int count = this.followStoreMapper.count(followStore);
        return count > 0;
    }

    @Override
    protected List<FollowStore> doFindListByCustomerId(String customerId) {
        return this.followStoreMapper.selectListByCustomerId(customerId);
    }

    @Override
    protected int doCountByCustomerId(String customerId) {
        return this.followStoreMapper.countByCustomerId(customerId);
    }
}
