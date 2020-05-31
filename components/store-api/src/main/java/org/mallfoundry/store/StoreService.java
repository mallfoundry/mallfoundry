package org.mallfoundry.store;

import org.mallfoundry.data.SliceList;

import java.util.Optional;

public interface StoreService {

    StoreQuery createStoreQuery();

    StoreId createStoreId(String id);

    Store createStore(String id);

    Store createStore(Store store);

    Store updateStore(Store store);

    void cancelStore(String id);

    Optional<Store> getStore(String id);

    SliceList<Store> getStores(StoreQuery query);

    StoreConfiguration getConfiguration(String storeId);

    void saveConfiguration(String storeId, StoreConfiguration configuration);
}
