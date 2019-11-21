package com.mallfoundry.storefront.domain;

public abstract class StoreRepositorySupport implements StoreRepository {

    protected abstract StoreInfo doFindById(String id);

    protected abstract void doUpdate(StoreInfo store);

    protected abstract void doAdd(StoreInfo store);

    @Override
    public StoreInfo findById(String id) {
        return this.doFindById(id);
    }

    @Override
    public void update(StoreInfo store) {
        this.doUpdate(store);
    }

    @Override
    public void add(StoreInfo store) {
        this.doAdd(store);
    }
}
