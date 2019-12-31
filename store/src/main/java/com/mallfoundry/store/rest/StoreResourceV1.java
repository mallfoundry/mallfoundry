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

import com.mallfoundry.store.StoreService;
import com.mallfoundry.store.StoreInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class StoreResourceV1 {

    private final StoreService storeService;

    public StoreResourceV1(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores/{id}")
    public Optional<StoreInfo> getStoreInfo(@PathVariable("id") String id) {
        return this.storeService.getStore(id);
    }

    @PostMapping("/stores")
    public void createStore(StoreInfo store) {
        this.storeService.createStore(store);
    }

}
