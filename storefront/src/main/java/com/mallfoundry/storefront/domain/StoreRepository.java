package com.mallfoundry.storefront.domain;

public interface StoreRepository {

    void add(StoreInfo store);

    void update(StoreInfo store);

    StoreInfo findById(String id);
}
