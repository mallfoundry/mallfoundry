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

package org.mallfoundry.storage;

import lombok.Setter;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.processor.Processors;
import org.mallfoundry.storage.acl.InternalOwner;
import org.mallfoundry.storage.acl.Owner;
import org.mallfoundry.storage.acl.OwnerType;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DefaultStorageService implements StorageService, StorageProcessorInvoker {

    @Setter
    private List<StorageProcessor> processors;

    private final StorageSystem storageSystem;

    private final BucketRepository bucketRepository;

    private final BlobRepository blobRepository;

    private final SharedBlobRepository sharedBlobRepository;

    public DefaultStorageService(StorageSystem storageSystem,
                                 BucketRepository bucketRepository,
                                 BlobRepository blobRepository,
                                 SharedBlobRepository sharedBlobRepository) {
        this.storageSystem = storageSystem;
        this.bucketRepository = bucketRepository;
        this.blobRepository = blobRepository;
        this.sharedBlobRepository = sharedBlobRepository;
    }

    @Override
    public Owner createOwner(OwnerType type, String name) {
        return new InternalOwner(type, name);
    }

    @Override
    public BucketId createBucketId(String id) {
        return new ImmutableBucketId(id);
    }

    @Override
    public Bucket createBucket(BucketId bucketId) {
        return this.bucketRepository.create(bucketId);
    }

    private Bucket requiredBucket(BucketId bucketId) {
        return this.bucketRepository.findById(bucketId)
                .orElseThrow(() -> new BucketException(StorageMessages.Bucket.notFound()));
    }

    @Override
    public Bucket getBucket(BucketId bucketId) throws BucketException {
        return this.bucketRepository.findById(bucketId)
                .map(this::invokePostProcessAfterGetBucket)
                .orElseThrow(() -> new BucketException(StorageMessages.Bucket.notFound()));
    }

    @Transactional
    @Override
    public Bucket addBucket(Bucket bucket) throws StorageException {
        bucket = this.invokePreProcessBeforeAddBucket(bucket);
        return this.bucketRepository.save(bucket);
    }

    @Transactional
    @Override
    public void deleteBucket(BucketId bucketId) throws BucketException {
        var bucket = this.requiredBucket(bucketId);
        this.blobRepository.deleteAllByBucketId(bucket.getId());
        this.bucketRepository.delete(bucket);
    }

    @Override
    public BlobResource createBlobResource() {
        return new FileBlobResource();
    }

    @Override
    public BlobId createBlobId(String bucketId, String blobId) {
        return new ImmutableBlobId(bucketId, blobId);
    }

    @Override
    public BlobQuery createBlobQuery() {
        return new DefaultBlobQuery();
    }

    @Override
    public Blob createBlob(BlobId blobId) {
        return this.blobRepository.create(blobId);
    }

    @Override
    public SliceList<Blob> getBlobs(BlobQuery query) {
        return this.blobRepository.findAll(query);
    }

    private boolean fetchSharedBlob(SharedBlob sharedBlob) {
        var existsSharedBlob = this.sharedBlobRepository.findByBlob(sharedBlob);
        if (Objects.isNull(existsSharedBlob)) {
            return false;
        }
        sharedBlob.setUrl(existsSharedBlob.getUrl());
        sharedBlob.setSize(existsSharedBlob.getSize());
        return true;
    }

    private void storeResource(BlobResource resource) throws IOException {
        try (resource) {
            var sharedBlob = SharedBlob.of(resource);
            if (this.fetchSharedBlob(sharedBlob)) {
                resource.setUrl(sharedBlob.getUrl());
                resource.setSize(sharedBlob.getSize());
            } else {
                this.storageSystem.store(resource);
                sharedBlob.setUrl(resource.getUrl());
                sharedBlob.setSize(resource.getSize());
                this.sharedBlobRepository.save(sharedBlob);
            }
        }
    }

    private Blob makeDirectories(BlobPath blobPath) {
        var path = blobPath.getParent();
        if (Objects.isNull(path)) {
            return null;
        }
        var parent = this.blobRepository.findById(path.toId()).orElse(null);
        if (Objects.isNull(parent)) {
            var blob = this.blobRepository.create(path);
            blob.makeDirectory();
            this.blobRepository.save(blob);
            this.makeDirectories(path); // 递归创建父目录。
        }
        return parent;
    }

    @Transactional
    @Override
    public Blob storeBlob(BlobResource resource) throws StorageException {
        var bucket = this.requiredBucket(this.createBucketId(resource.getBucketId()));
        var path = resource.toPath();
        var blob = this.createBlob(path).toBuilder()
                .name(resource.getName()).path(resource.getPath())
                .contentType(resource.getContentType())
                .build();
        blob = this.invokePreProcessBeforeStoreBlob(bucket, blob);
        blob.moveTo(this.makeDirectories(path));
        blob.create();
        if (BlobType.FILE.equals(blob.getType())) {
            try {
                resource.setPath(blob.getPath());
                this.storeResource(resource);
                blob.setSize(resource.getSize());
                blob.setUrl(resource.getUrl());
            } catch (IOException e) {
                throw new StorageException(e);
            }
        }
        return this.blobRepository.save(blob);
    }

    private Blob requiredBlob(BlobId blobId) {
        return this.blobRepository.findById(blobId)
                .orElseThrow(() -> new BlobException(StorageMessages.Blob.notFound()));
    }

    @Transactional
    @Override
    public Blob updateBlob(Blob source) {
        var blob = this.requiredBlob(source.toId());
        blob.rename(source.getName());
        return this.blobRepository.save(blob);
    }

    @Transactional
    @Override
    public void deleteBlob(BlobId blobId) {
        var blob = this.requiredBlob(blobId);
        this.blobRepository.delete(blob);
    }

    @Transactional
    @Override
    public void deleteBlobs(Set<BlobId> blobIds) {
        var blobs = this.blobRepository.findAllById(blobIds);
        this.blobRepository.deleteAll(blobs);
    }

    @Override
    public Bucket invokePostProcessAfterGetBucket(Bucket bucket) {
        return Processors.stream(this.processors)
                .map(StorageProcessor::postProcessAfterGetBucket)
                .apply(bucket);
    }

    @Override
    public Bucket invokePreProcessBeforeAddBucket(Bucket bucket) {
        return Processors.stream(this.processors)
                .map(StorageProcessor::preProcessBeforeAddBucket)
                .apply(bucket);
    }

    @Override
    public Blob invokePreProcessBeforeStoreBlob(Bucket bucket, Blob blob) {
        return Processors.stream(this.processors)
                .<Blob>map((processor, identity) -> processor.preProcessBeforeStoreBlob(bucket, identity))
                .apply(blob);
    }
}
