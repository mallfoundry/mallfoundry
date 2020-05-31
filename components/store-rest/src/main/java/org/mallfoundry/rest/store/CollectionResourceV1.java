package org.mallfoundry.rest.store;

import org.mallfoundry.store.CollectionService;
import org.mallfoundry.store.CustomCollection;
import org.mallfoundry.store.StoreService;
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

    private final StoreService storeService;

    private final CollectionService collectionService;

    public CollectionResourceV1(StoreService storeService, CollectionService collectionService) {
        this.storeService = storeService;
        this.collectionService = collectionService;
    }

    @PostMapping("/stores/{store_id}/custom-collections")
    public CustomCollection addCollection(@PathVariable("store_id") String storeId,
                                          @RequestBody CollectionRequest request) {
        var newCollection = this.collectionService.createCollection(storeId, request.getName());
        return this.collectionService.addCollection(newCollection);
    }

    @PutMapping("/stores/{store_id}/custom-collections/{collection_id}")
    public void updateCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") String collectionId,
                                 @RequestBody CollectionRequest request) {
        Assert.notNull(storeId, "Store id must not be null");
        Assert.notNull(collectionId, "Collection id must not be null");
        this.collectionService.updateCollection(
                request.assignToCollection(
                        this.collectionService.createCollection(collectionId)));
    }


    @DeleteMapping("/stores/{store_id}/custom-collections/{collection_id}")
    public void deleteCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") String id) {
        Assert.notNull(storeId, "Store id must not be null");
        this.collectionService.deleteCollection(id);
    }

    @GetMapping("/stores/{store_id}/custom-collections")
    public List<CustomCollection> getCollections(@PathVariable("store_id") String storeId) {
        return this.collectionService.getCollections(this.storeService.createStoreId(storeId));
    }

}
