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

package org.mallfoundry.rest.catalog.collection;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mallfoundry.catalog.collection.Collection;
import org.mallfoundry.catalog.collection.CollectionService;
import org.mallfoundry.data.SliceList;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product Collections")
@RestController
@RequestMapping("/v1")
public class CollectionResourceV1 {
    private final CollectionService collectionService;

    public CollectionResourceV1(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/collections")
    public Collection addCollection(@RequestBody CollectionRequest request) {
        return this.collectionService.addCollection(
                request.assignTo(this.collectionService.createCollection(null)));
    }

    @GetMapping("/collections")
    public SliceList<Collection> getCollections(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                                @RequestParam("store_id") String storeId) {
        return this.collectionService.getCollections(
                this.collectionService.createCollectionQuery()
                        .toBuilder().page(page).limit(limit).storeId(storeId).build());
    }

    @PatchMapping("/collections/{collection_id}")
    public Collection updateCollection(@PathVariable("collection_id") String collectionId,
                                       @RequestBody CollectionRequest request) {
        Assert.notNull(collectionId, "Collection id must not be null");
        return this.collectionService.updateCollection(
                request.assignTo(
                        this.collectionService.createCollection(collectionId)));
    }


    @DeleteMapping("/collections/{collection_id}")
    public void deleteCollection(@PathVariable("store_id") String storeId,
                                 @PathVariable("collection_id") String id) {
        Assert.notNull(storeId, "Store id must not be null");
        this.collectionService.deleteCollection(id);
    }
}
