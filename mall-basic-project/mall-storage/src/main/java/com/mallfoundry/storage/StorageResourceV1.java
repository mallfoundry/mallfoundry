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

package com.mallfoundry.storage;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/v1/storage")
@RestController
public class StorageResourceV1 {

    private final AntPathMatcher objectPathMatcher = new AntPathMatcher();

    private final StorageSystem storageSystem;

    public StorageResourceV1(StorageSystem storageSystem) {
        this.storageSystem = storageSystem;
    }

    @PostMapping("/buckets/{bucket}/objects/**")
    public StorageObject storeObject(@PathVariable("bucket") String bucket,
                                     @RequestParam("file") MultipartFile file,
                                     HttpServletRequest request) throws IOException {
        return this.storageSystem.storeObject(new ObjectResource(bucket, getObjectPath(request), file.getInputStream()));
    }

    @PostMapping("/buckets/{bucket}/images/**")
    public StorageObject storeImage(@PathVariable("bucket") String bucket,
                                    @RequestParam("file") MultipartFile file,
                                    HttpServletRequest request) throws IOException {
        return this.storageSystem.storeObject(new ObjectResource(bucket, getObjectPath(request), file.getInputStream()));
    }

    private String getObjectPath(HttpServletRequest request) {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        return this.objectPathMatcher.extractPathWithinPattern(bestMatchingPattern, path);
    }
}
