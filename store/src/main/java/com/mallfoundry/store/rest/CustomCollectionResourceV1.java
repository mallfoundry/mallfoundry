package com.mallfoundry.store.rest;

import com.mallfoundry.store.CustomCollectionService;
import com.mallfoundry.store.StoreId;
import com.mallfoundry.store.TopCustomCollection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomCollectionResourceV1 {

    private final CustomCollectionService customCollectionService;

    public CustomCollectionResourceV1(CustomCollectionService customCollectionService) {
        this.customCollectionService = customCollectionService;
    }

    @GetMapping("/stores/{store_id}/custom_collections")
    public List<TopCustomCollection> getCollections(
            @PathVariable("store_id") String storeId) {
        return this.customCollectionService.getAllCollections(new StoreId(storeId));
    }

}
