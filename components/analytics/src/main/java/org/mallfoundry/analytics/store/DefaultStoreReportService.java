package org.mallfoundry.analytics.store;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultStoreReportService implements StoreReportService {

    private final StoreTotalOrderQuantityRepository storeTotalOrderQuantityRepository;

    public DefaultStoreReportService(StoreTotalOrderQuantityRepository storeTotalOrderQuantityRepository) {
        this.storeTotalOrderQuantityRepository = storeTotalOrderQuantityRepository;
    }

    @Override
    public Optional<StoreTotalOrderQuantity> countTotalOrderQuantity(String storeId) {
        return this.storeTotalOrderQuantityRepository.findByStoreId(storeId);
    }
}
