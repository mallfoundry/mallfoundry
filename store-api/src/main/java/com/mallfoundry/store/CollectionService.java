package com.mallfoundry.store;

import java.util.List;
import java.util.Optional;

public interface CollectionService {

    CustomCollection createCollection(String storeId, String name);

    CustomCollection saveCollection(CustomCollection collection);

    void deleteCollection(String id);

    Optional<CustomCollection> getCollection(String id);

    List<CustomCollection> getCollections(StoreId storeId);

}
