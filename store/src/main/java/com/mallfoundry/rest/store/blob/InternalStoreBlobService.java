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

package com.mallfoundry.rest.store.blob;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.rest.store.StoreId;
import com.mallfoundry.storage.Blob;
import com.mallfoundry.storage.BlobQuery;
import com.mallfoundry.storage.Bucket;
import com.mallfoundry.storage.StorageService;
import com.mallfoundry.util.PathUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InternalStoreBlobService implements StoreBlobService {

    private static final String STORE_OWNER_TYPE = "store";

    private static final String STORE_BUCKET_PREFIX = "store-";

    private final StorageService storageService;

    public InternalStoreBlobService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public BlobQuery createBlobQuery() {
        return this.storageService.createBlobQuery();
    }

    @Override
    public String getBucketName(StoreId storeId) {
        return STORE_BUCKET_PREFIX + storeId.getIdentifier();
    }

    @Override
    public Optional<Bucket> getBucket(StoreId storeId) {
        return this.storageService.getBucket(this.getBucketName(storeId));
    }

    @Transactional
    @Override
    public void initializeBucket(StoreId storeId) {
        var owner = this.storageService.createOwner(STORE_OWNER_TYPE, storeId.getIdentifier());
        var bucket = this.storageService.createBucket(this.getBucketName(storeId), owner);
        this.storageService.saveBucket(bucket);
    }

    @Override
    public Blob storeBlob(Blob blob) throws IOException, StoreBlobException {
        try (blob) {
            PathValidator.validate(blob.getPath());
            if (Extensions.isImageExtension(blob) || blob.isDirectory()) {
                return this.storageService.storeBlob(blob);
            }
            throw new StoreBlobException("An unrecognized file extension");
        }
    }

    @Override
    public void deleteBlob(StoreId storeId, String path) {
        this.storageService.deleteBlob(getBucketName(storeId), PathUtils.normalize(path));
    }

    @Override
    public void deleteBlobs(StoreId storeId, List<String> paths) {
        this.storageService.deleteBlobs(getBucketName(storeId),
                paths.stream().map(PathUtils::normalize).collect(Collectors.toList()));
    }

    @Override
    public SliceList<Blob> getBlobs(BlobQuery query) {
        PathValidator.validate(query.getPath());
        return this.storageService.getBlobs(query);
    }

    static abstract class PathValidator {
        private static final Set<String> PATH_PREFIXES = Set.of("images", "/images");

        public static void validate(String path) throws StoreBlobException {
            for (var prefix : PATH_PREFIXES) {
                if (StringUtils.startsWithIgnoreCase(path, prefix)) {
                    return;
                }
            }
            throw new StoreBlobException("Path error for blob");
        }
    }

    static abstract class Extensions {

        private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg");

        public static boolean isImageExtension(Blob blob) {
            String extension = FilenameUtils.getExtension(blob.getPath());
            return IMAGE_EXTENSIONS.contains(extension);
        }
    }
}
