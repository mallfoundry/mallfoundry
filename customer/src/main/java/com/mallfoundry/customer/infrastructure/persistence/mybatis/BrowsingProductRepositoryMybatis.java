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

package com.mallfoundry.customer.infrastructure.persistence.mybatis;

import com.mallfoundry.customer.domain.BrowsingProduct;
import com.mallfoundry.customer.domain.BrowsingProductQuery;
import com.mallfoundry.customer.domain.BrowsingProductRepositorySupport;
import com.mallfoundry.data.OffsetList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrowsingProductRepositoryMybatis extends BrowsingProductRepositorySupport {

    private final BrowsingProductMapper browsingProductMapper;

    public BrowsingProductRepositoryMybatis(BrowsingProductMapper browsingProductMapper) {
        this.browsingProductMapper = browsingProductMapper;
    }

    @Override
    protected void doAdd(BrowsingProduct browsingProduct) {
        this.browsingProductMapper.insert(browsingProduct);
    }

    @Override
    protected void doDelete(BrowsingProduct browsingProduct) {
        this.browsingProductMapper.delete(browsingProduct);
    }

    @Override
    protected void doDelete(List<BrowsingProduct> browsingProducts) {
        this.browsingProductMapper.batchDelete(browsingProducts);
    }

    @Override
    protected void doUpdate(BrowsingProduct browsingProduct) {
        this.browsingProductMapper.update(browsingProduct);
    }

    @Override
    public boolean exists(BrowsingProduct browsingProduct) {
        return this.browsingProductMapper.count(browsingProduct) > 0;
    }

    @Override
    public OffsetList<BrowsingProduct> findListByQuery(BrowsingProductQuery query) {
        int count = this.browsingProductMapper.countByQuery(query);
        List<BrowsingProduct> browsingProducts = this.browsingProductMapper.selectListByQuery(query);
        return OffsetList.of(browsingProducts, query.getOffset(), query.getLimit(), count);
    }

    @Override
    public int countByCustomerId(String customerId) {
        return this.browsingProductMapper.countByCustomerId(customerId);
    }
}
