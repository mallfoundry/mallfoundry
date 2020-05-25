/*
 * Copyright 2020 the original author or authors.
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

package org.mallfoundry.cache;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class CacheResourceV1 {

    private final CacheManager cacheManager;

    public CacheResourceV1(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @DeleteMapping("/caches")
    public void clearCaches() {
        this.cacheManager.getCacheNames()
                .forEach(name -> Objects.requireNonNull(this.cacheManager.getCache(name)).clear());
    }
}
