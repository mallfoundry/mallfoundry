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

package com.mallfoundry.store.blob;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.storage.Blob;
import com.mallfoundry.storage.BlobQuery;
import com.mallfoundry.storage.Bucket;
import com.mallfoundry.storage.StorageService;
import com.mallfoundry.store.StoreId;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class InternalStoreBlobService implements StoreBlobService {

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
        return STORE_BUCKET_PREFIX + storeId.identity();
    }

    @Override
    public Optional<Bucket> getBucket(StoreId storeId) {
        return this.storageService.getBucket(this.getBucketName(storeId));
    }

    @Override
    public Blob storeBlob(Blob blob) throws IOException, StoreBlobException {
        try (blob) {
            PathValidator.validate(blob.getPath());
            if (Extensions.isImageExtension(blob)) {
                return this.storageService.storeBlob(blob);
            }
            throw new StoreBlobException("An unrecognized file extension");
        }
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
