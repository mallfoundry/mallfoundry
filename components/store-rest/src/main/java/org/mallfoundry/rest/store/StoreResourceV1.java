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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.Store;
import org.mallfoundry.store.StoreService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    private final StoreService storeService;

    public StoreResourceV1(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores/{store_id}/configuration")
    public Map<String, String> getConfiguration(@PathVariable("store_id") String storeId) {
        return this.storeService.getConfiguration(storeId).toMap();
    }

    @PutMapping("/stores/{store_id}/configuration")
    public void saveConfiguration(@PathVariable("store_id") String storeId,
                                  @RequestBody Map<String, String> map) {
        if (Objects.nonNull(map)) {
            var config = this.storeService.getConfiguration(storeId);
            config.clear();
            map.forEach(config::set);
            this.storeService.saveConfiguration(storeId, config);
        }
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody StoreRequest request) {
        var store = this.storeService.createStore(request.getId());
        store.setName(request.getName());
        store.setLogoUrl(request.getLogoUrl());
        store.setDescription(request.getDescription());
        return this.storeService.createStore(store);
    }

    @DeleteMapping("/stores/{store_id}")
    public void cancelStore(@PathVariable("store_id") String storeId) {
        this.storeService.cancelStore(storeId);
    }

    @PatchMapping("/stores/{store_id}")
    public void updateStore(@PathVariable("store_id") String storeId,
                            @RequestBody StoreRequest request) {
        var store = this.storeService.createStore(storeId);
        this.storeService.updateStore(request.assignToStore(store));
    }

    @GetMapping("/stores/{id}")
    public Optional<Store> getStore(@PathVariable("id") String id) {
        return this.storeService.getStore(id);
    }

    @GetMapping("/stores")
    public SliceList<Store> getStores(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                      @RequestParam(name = "owner_id") String ownerId) {
        return this.storeService.getStores(this.storeService.createStoreQuery().toBuilder().page(page).limit(limit).ownerId(ownerId).build());
    }

}
