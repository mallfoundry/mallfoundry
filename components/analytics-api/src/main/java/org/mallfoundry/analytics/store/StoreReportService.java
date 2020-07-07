package org.mallfoundry.analytics.store;

import java.util.Optional;

public interface StoreReportService {
    Optional<StoreTotalOrderQuantity> countTotalOrderQuantity(String storeId);
}
