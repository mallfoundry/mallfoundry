package com.mallfoundry.storefront.rest;


import com.mallfoundry.storefront.application.CustomCollectionService;
import com.mallfoundry.storefront.domain.product.CustomCollection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<CustomCollection> getCollections(@PathVariable("store_id") String storeId,
                                                    @RequestParam(name = "top", required = false) boolean top,
                                                    @RequestParam(name = "parent_id", required = false) String parentId) {
        if (top) {
            return this.customCollectionService.getTopCollections(storeId);
        }
        return this.customCollectionService.getCollections(parentId);
    }

    @PostMapping("/stores/{store_id}/custom_collections")
    public void addCollection(@PathVariable("store_id") String storeId,
                              @RequestBody CustomCollection collection) {
        collection.setStoreId(storeId);
        this.customCollectionService.addCollection(collection);
    }
}
