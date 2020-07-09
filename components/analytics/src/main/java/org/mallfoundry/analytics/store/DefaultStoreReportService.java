package org.mallfoundry.analytics.store;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultStoreReportService implements StoreReportService {

    private final StoreTotalOrderQuantityRepository storeTotalOrderQuantityRepository;

    private final StoreTotalProductQuantityRepository storeTotalProductQuantityRepository;

    private final StoreTotalOrderQuantity emptyStoreTotalOrderQuantity = new DefaultStoreTotalOrderQuantity();

    private final StoreTotalProductQuantity emptyStoreTotalProductQuantity = new DefaultStoreTotalProductQuantity();

    public DefaultStoreReportService(StoreTotalOrderQuantityRepository storeTotalOrderQuantityRepository,
                                     StoreTotalProductQuantityRepository storeTotalProductQuantityRepository) {
        this.storeTotalOrderQuantityRepository = storeTotalOrderQuantityRepository;
        this.storeTotalProductQuantityRepository = storeTotalProductQuantityRepository;
    }

    @Override
    public Optional<StoreTotalOrderQuantity> countTotalOrderQuantity(String storeId) {
        return this.storeTotalOrderQuantityRepository.findByStoreId(storeId)
                .or(() -> Optional.of(emptyStoreTotalOrderQuantity));
    }

    @Override
    public Optional<StoreTotalProductQuantity> countTotalProductQuantity(String storeId) {
        return this.storeTotalProductQuantityRepository.findByStoreId(storeId)
                .or(() -> Optional.of(emptyStoreTotalProductQuantity));
    }
}
