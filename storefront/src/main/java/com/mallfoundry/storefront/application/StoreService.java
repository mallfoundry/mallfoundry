package com.mallfoundry.storefront.application;

import com.mallfoundry.storefront.domain.StoreInfo;
import com.mallfoundry.storefront.domain.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreInfo getStoreInfo(String id) {
        return this.storeRepository.findById(id);
    }

    @Transactional
    public void createStoreInfo(StoreInfo store) {
        this.storeRepository.add(store);
    }
}
