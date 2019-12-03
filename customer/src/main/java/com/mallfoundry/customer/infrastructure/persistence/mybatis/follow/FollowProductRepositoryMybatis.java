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

import com.mallfoundry.customer.domain.follow.FollowProduct;
import com.mallfoundry.customer.domain.follow.FollowProductRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowProductRepositoryMybatis extends FollowProductRepositorySupport {

    private final FollowProductMapper followProductMapper;

    public FollowProductRepositoryMybatis(FollowProductMapper followProductMapper) {
        this.followProductMapper = followProductMapper;
    }

    @Override
    protected void doAdd(FollowProduct followProduct) {
        this.followProductMapper.insert(followProduct);
    }

    @Override
    protected void doDelete(FollowProduct followProduct) {
        this.followProductMapper.delete(followProduct);
    }

    @Override
    protected boolean doExist(FollowProduct followProduct) {
        int count = this.followProductMapper.count(followProduct);
        return count > 0;
    }

    @Override
    protected List<FollowProduct> doFindListByCustomerId(String customerId) {
        return this.followProductMapper.selectListByCustomerId(customerId);
    }

    @Override
    protected int doCountByCustomerId(String customerId) {
        return this.followProductMapper.countByCustomerId(customerId);
    }
}
