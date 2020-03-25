package com.mallfoundry.store.rest;

import com.mallfoundry.store.CollectionService;
import com.mallfoundry.store.CustomCollection;
import com.mallfoundry.store.StoreId;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CollectionResourceV1 {

    private final CollectionService collectionService;

    public CollectionResourceV1(CollectionService customCollectionService) {
        this.collectionService = customCollectionService;
    }

    @PostMapping("/stores/{store_id}/custom-collections")
    public CustomCollection saveCollection(@PathVariable("store_id") String storeId,
                                           @RequestBody CustomCollection collection) {
        CustomCollection newCollection =
                this.collectionService.createCollection(StoreId.of(storeId), collection.getName());
        return this.collectionService.saveCollection(newCollection);
    }

    @PutMapping("/stores/{store_id}/custom-collections/{collection_id}")
    public void updateCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") Long collectionId,
                                 @RequestBody CustomCollection collection) {
        Assert.notNull(storeId, "Store id must not be null");
        Assert.notNull(collectionId, "Collection id must not be null");
        this.collectionService
                .getCollection(collectionId)
                .ifPresent(oldCollection -> {
                    oldCollection.setName(collection.getName());
                    this.collectionService.saveCollection(oldCollection);
                });
    }


    @DeleteMapping("/stores/{store_id}/custom-collections/{collection_id}")
    public void deleteCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") Long id) {
        Assert.notNull(storeId, "Store id must not be null");
        this.collectionService.deleteCollection(id);
    }

    @GetMapping("/stores/{store_id}/custom-collections")
    public List<CustomCollection> getCollections(@PathVariable("store_id") String storeId) {
        return this.collectionService.getCollections(StoreId.of(storeId));
    }

}
