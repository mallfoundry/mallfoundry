package com.mallfoundry.storefront.application;

import com.mallfoundry.storefront.domain.product.CustomCollection;
import com.mallfoundry.storefront.domain.product.CustomCollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomCollectionService {

    private final CustomCollectionRepository customCollectionRepository;

    public CustomCollectionService(CustomCollectionRepository customCollectionRepository) {
        this.customCollectionRepository = customCollectionRepository;
    }

    @Transactional
    public void addCollection(CustomCollection collection) {
        collection.validStoreId();
        collection.validParentId();
        this.customCollectionRepository.add(collection);
    }

    public List<CustomCollection> getTopCollections(String storeId) {
        return this.customCollectionRepository.findTopList(storeId);
    }

    public List<CustomCollection> getCollections(String parentId) {
        return this.customCollectionRepository.findListByParentId(parentId);
    }
}
