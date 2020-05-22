package com.mallfoundry.rest.store;

import com.mallfoundry.data.SliceList;

import java.util.Optional;

public interface StoreService {

    StoreQuery createStoreQuery();

    StoreId createStoreId(String id);

    Store createStore(String id);

    Store initializeStore(Store store);

    Store saveStore(Store store);

    void cancelStore(String id);

    Optional<Store> getStore(String id);

    SliceList<Store> getStores(StoreQuery query);

    StoreConfiguration getConfiguration(String storeId);

    void saveConfiguration(String storeId, StoreConfiguration configuration);
}
