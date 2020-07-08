package org.mallfoundry.analytics.store;

import java.util.Optional;

public interface StoreTotalProductQuantityRepository {
    Optional<StoreTotalProductQuantity> findByStoreId(String storeId);
}
