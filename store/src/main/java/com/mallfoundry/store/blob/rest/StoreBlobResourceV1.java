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

package com.mallfoundry.store.blob.rest;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.storage.Blob;
import com.mallfoundry.storage.BlobType;
import com.mallfoundry.store.StoreService;
import com.mallfoundry.store.blob.StoreBlobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequestMapping("/v1/stores")
@RestController
public class StoreBlobResourceV1 {

    private final AntPathMatcher blobPathMatcher = new AntPathMatcher();

    private final StoreService storeService;

    private final StoreBlobService storeBlobService;

    public StoreBlobResourceV1(StoreService storeService, StoreBlobService storeBlobService) {
        this.storeService = storeService;
        this.storeBlobService = storeBlobService;
    }

    @PostMapping("/{store_id}/blobs/**")
    public Blob storeBlob(
            @PathVariable("store_id") String storeIdString,
            @RequestParam(required = false) String name,
            @RequestParam(name = "file", required = false) MultipartFile file,
            HttpServletRequest request) throws IOException {
        var storeId = this.storeService.createStoreId(storeIdString);
        var bucket = this.storeBlobService.getBucket(storeId).orElseThrow();

        Blob storeBlob;
        if (Objects.nonNull(file)) {
            String blobPath = extractBlobPath(request, file.getOriginalFilename());
            storeBlob = bucket.createBlob(blobPath, file.getInputStream());
            storeBlob.rename(StringUtils.isEmpty(name) ? file.getOriginalFilename() : name);
        } else {
            storeBlob = bucket.createBlob(extractBlobPath(request, name));
            if (StringUtils.isNotBlank(name)) {
                storeBlob.rename(name);
            }
        }
        return this.storeBlobService.storeBlob(storeBlob);
    }


    @DeleteMapping("/{store_id}/blobs/**")
    public void deleteBlob(@PathVariable("store_id") String storeIdString,
                           HttpServletRequest request) {
        var storeId = this.storeService.createStoreId(storeIdString);
        this.storeBlobService.deleteBlob(storeId, extractBlobPath(request, null));
    }

    @DeleteMapping("/{store_id}/blobs/batch")
    public void deleteBlobs(@PathVariable("store_id") String storeIdString,
                            @RequestBody List<String> paths) {
        var storeId = this.storeService.createStoreId(storeIdString);
        this.storeBlobService.deleteBlobs(storeId, paths);
    }

    @GetMapping("/{store_id}/blobs")
    public SliceList<Blob> getBlobs(
            @PathVariable("store_id") String storeId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            String type, String path) {
        var typeUpper = StringUtils.upperCase(type);
        var bucketName = this.storeBlobService.getBucketName(this.storeService.createStoreId(storeId));
        return this.storeBlobService.getBlobs(this.storeBlobService.createBlobQuery().toBuilder()
                .page(page).limit(limit)
                .bucket(bucketName)
                .type(StringUtils.isEmpty(typeUpper) ? null : BlobType.valueOf(typeUpper))
                .path(path).build());
    }

    private String extractBlobPath(HttpServletRequest request, String defaultPath) {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        String bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        String blobPath = this.blobPathMatcher.extractPathWithinPattern(bestMatchingPattern, path);
        return StringUtils.isNotEmpty(blobPath) ? blobPath : defaultPath;
    }
}
