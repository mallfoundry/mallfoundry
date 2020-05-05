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

import com.mallfoundry.data.SliceList;
import com.mallfoundry.security.SecurityUserHolder;
import com.mallfoundry.store.InternalStore;
import com.mallfoundry.store.InternalStoreService;
import com.mallfoundry.store.StoreQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class StoreResourceV1 {

    private final InternalStoreService storeService;

    public StoreResourceV1(InternalStoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/stores")
    public void createStore(@RequestBody InternalStore store) {
        store.setOwnerId(SecurityUserHolder.getUserId());
        this.storeService.createStore(store);
    }

    @DeleteMapping("/stores/{store_id}")
    public void cancelStore(@PathVariable("store_id") String storeId) {
        this.storeService.cancelStore(storeId);
    }

    @PutMapping("/stores/{store_id}")
    public void saveStore(@PathVariable("store_id") String storeId,
                          @RequestBody StoreRequest request) {
        var store = this.storeService.getStore(storeId).orElseThrow();

        if (Objects.nonNull(request.getLogoUrl())) {
            store.setLogoUrl(request.getLogoUrl());
        }

        if (Objects.nonNull(request.getLogoUrl())) {
            store.setDescription(request.getDescription());
        }

        this.storeService.saveStore(store);
    }

    @GetMapping("/stores/{store_id}/configuration")
    public Map<String, String> getConfiguration(@PathVariable("store_id") String storeId) {
        return this.storeService.getConfiguration(storeId).toMap();
    }

    @PostMapping("/stores/{store_id}/configuration")
    public void saveConfiguration(@PathVariable("store_id") String storeId,
                                 @RequestBody Map<String, String> map) {
        if (Objects.nonNull(map)) {
            var config = this.storeService.getConfiguration(storeId);
            map.forEach(config::set);
            this.storeService.saveConfiguration(storeId, config);
        }
    }

    @GetMapping("/stores/{id}")
    public Optional<InternalStore> getStore(@PathVariable("id") String id) {
        return this.storeService.getStore(id);
    }

    @GetMapping("/stores")
    public SliceList<InternalStore> getStores(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "owner_id") String ownerId) {
        return this.storeService.getStores(StoreQuery.builder().page(page).limit(limit).ownerId(ownerId).build());
    }

}
