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

package org.mallfoundry.rest.storage;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobType;
import org.mallfoundry.storage.Bucket;
import org.mallfoundry.storage.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class StorageResourceV1 {

    private final StorageService storageService;

    public StorageResourceV1(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/buckets/{bucket_id}")
    public Bucket getBucket(@PathVariable(name = "bucket_id") String bucketId) {
        return this.storageService.getBucket(this.storageService.createBucketId(bucketId));
    }

    private Blob createBlob(Bucket bucket, String path, String name, MultipartFile file) throws IOException {
        if (Objects.isNull(file)) {
            var filename = Objects.isNull(name) ? FilenameUtils.getName(path) : name;
            return bucket.createBlob(path).toBuilder().name(filename).build();
        }
        var filename = Objects.isNull(name) ? file.getOriginalFilename() : name;
        return bucket.createBlob(path, file.getInputStream()).toBuilder().name(filename).build();
    }

    @PostMapping("/buckets/{bucket_id}/blobs")
    public BlobResponse storeBlob(@PathVariable(name = "bucket_id") String bucketId,
                                  @RequestParam String path,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) MultipartFile file) throws IOException {
        var bucket = this.storageService.createBucket(
                this.storageService.createBucketId(bucketId));
        var blob = this.createBlob(bucket, path, name, file);
        return BlobResponse.of(this.storageService.storeBlob(blob));
    }

    @GetMapping("/buckets/{bucket_id}/blobs")
    public SliceList<BlobResponse> getBlobs(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                            @PathVariable(name = "bucket_id") String bucketId,
                                            @RequestParam(name = "types", required = false) Set<String> types,
                                            @RequestParam(required = false) String path) {
        return this.storageService.getBlobs(
                this.storageService.createBlobQuery().toBuilder()
                        .page(page).limit(limit)
                        .bucketId(bucketId).path(path)
                        .types(() ->
                                CollectionUtils.emptyIfNull(types).stream().map(StringUtils::upperCase)
                                        .map(BlobType::valueOf).collect(Collectors.toUnmodifiableSet()))
                        .build())
                .map(BlobResponse::of);
    }
}
