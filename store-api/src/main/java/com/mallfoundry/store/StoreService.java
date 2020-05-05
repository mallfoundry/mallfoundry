package com.mallfoundry.store;

public interface StoreService {

    StoreId createStoreId(String id);

    Store createStore();

    StoreConfiguration getConfiguration(String storeId);

    void saveConfiguration(String storeId, StoreConfiguration configuration);
}
