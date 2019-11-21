package com.mallfoundry.storefront.infrastructure.persistence.mybatis;

import com.mallfoundry.storefront.domain.StoreInfo;
import com.mallfoundry.storefront.domain.StoreRepositorySupport;
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
