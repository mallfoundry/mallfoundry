/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.store.rest;


import com.mallfoundry.store.application.CustomCollectionService;
import com.mallfoundry.store.domain.CustomCollection;
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
