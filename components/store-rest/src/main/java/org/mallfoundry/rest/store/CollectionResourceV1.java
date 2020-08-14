/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.rest.store;

import org.mallfoundry.catalog.collection.ProductCollection;
import org.mallfoundry.catalog.collection.ProductCollectionService;
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
    private final ProductCollectionService collectionService;

    public CollectionResourceV1(ProductCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/stores/{store_id}/collections")
    public ProductCollection addCollection(@PathVariable("store_id") String storeId,
                                           @RequestBody CollectionRequest request) {
        return this.collectionService.addCollection(
                request.assignToCollection(
                        this.collectionService.createCollection(null).toBuilder().storeId(storeId).build()));
    }

    @PutMapping("/stores/{store_id}/collections/{collection_id}")
    public void updateCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") String collectionId,
                                 @RequestBody CollectionRequest request) {
        Assert.notNull(storeId, "Store id must not be null");
        Assert.notNull(collectionId, "Collection id must not be null");
        this.collectionService.updateCollection(
                request.assignToCollection(
                        this.collectionService.createCollection(collectionId)));
    }


    @DeleteMapping("/stores/{store_id}/collections/{collection_id}")
    public void deleteCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") String id) {
        Assert.notNull(storeId, "Store id must not be null");
        this.collectionService.deleteCollection(id);
    }

    @GetMapping("/stores/{store_id}/collections")
    public List<ProductCollection> getCollections(@PathVariable("store_id") String storeId) {
        return this.collectionService.getCollections(storeId);
    }

}
