package com.mallfoundry.store;

import java.util.List;
import java.util.Optional;

public interface StoreConfigPropertyRepository {

    List<StoreConfigProperty> findAllByStoreId(String storeId);

    StoreConfigProperty save(StoreConfigProperty property);

    Optional<StoreConfigProperty> findById(StoreConfigPropertyId id);

    void deleteById(StoreConfigPropertyId id);
}
