package com.mallfoundry.storefront.domain.product;

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
