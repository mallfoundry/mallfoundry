package org.mallfoundry.analytics.store;

import java.util.Optional;

public interface StoreTotalOrderQuantityRepository {
    Optional<StoreTotalOrderQuantity> findByStoreId(String storeId);
}
