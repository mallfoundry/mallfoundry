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

package org.mallfoundry.store.blob;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobType;
import org.mallfoundry.storage.Bucket;
import org.mallfoundry.storage.StorageService;
import org.mallfoundry.storage.acl.OwnerType;
import org.mallfoundry.store.StoreId;
import org.mallfoundry.util.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultStoreBlobService implements StoreBlobService {

    private static final String STORE_BUCKET_PREFIX = "store-";

    private final StorageService storageService;

    public DefaultStoreBlobService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public StoreBlobQuery createStoreBlobQuery() {
        return new DefaultStoreBlobQuery();
    }

    private String getStoreBucketName(StoreId storeId) {
        return this.getStoreBucketName(storeId.getId());
    }

    private String getStoreBucketName(String storeId) {
        return STORE_BUCKET_PREFIX + storeId;
    }

    @Override
    public Bucket getStoreBucket(StoreId storeId) {
        return this.storageService.getBucket(this.getStoreBucketName(storeId));
    }

    @Transactional
    @Override
    public void initializeStoreBucket(StoreId storeId) {
        var owner = this.storageService.createOwner(OwnerType.STORE, storeId.getId());
        var bucket = this.storageService.createBucket(this.getStoreBucketName(storeId), owner);
        this.storageService.addBucket(bucket);
    }

    @Override
    public Blob storeStoreBlob(Blob blob) throws IOException, StoreBlobException {
        PathValidator.validate(blob.getPath());
        if (Extensions.isImageExtension(blob) || BlobType.DIRECTORY.equals(blob.getType())) {
            try (blob) {
                return this.storageService.storeBlob(blob);
            }
        }
        throw new StoreBlobException("An unrecognized file extension");
    }

    @Override
    public SliceList<Blob> getStoreBlobs(StoreBlobQuery query) {
        PathValidator.validate(query.getPath());
        var blobQuery = this.storageService.createBlobQuery().toBuilder()
                .page(query.getPage()).limit(query.getLimit())
                .bucket(this.getStoreBucketName(query.getStoreId()))
                .type(query.getType())
                .path(query.getPath())
                .build();
        return this.storageService.getBlobs(blobQuery);
    }

    @Override
    public void deleteStoreBlob(StoreId storeId, String path) {
        this.storageService.deleteBlob(getStoreBucketName(storeId), PathUtils.normalize(path));
    }

    @Override
    public void deleteStoreBlobs(StoreId storeId, List<String> paths) {
        this.storageService.deleteBlobs(getStoreBucketName(storeId),
                paths.stream().map(PathUtils::normalize).collect(Collectors.toList()));
    }

    @Override
    public void clearStoreBlobs(StoreId storeId) {
        this.storageService.deleteBucket(this.getStoreBucketName(storeId));
    }

    abstract static class PathValidator {
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

    abstract static class Extensions {

        private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg");

        public static boolean isImageExtension(Blob blob) {
            String extension = FilenameUtils.getExtension(blob.getPath());
            return IMAGE_EXTENSIONS.contains(extension);
        }
    }
}
