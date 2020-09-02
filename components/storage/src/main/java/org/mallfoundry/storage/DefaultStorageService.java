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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.acl.InternalOwner;
import org.mallfoundry.storage.acl.Owner;
import org.mallfoundry.storage.acl.OwnerType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultStorageService implements StorageService {

    private final StorageSystem storageSystem;

    private final InternalBucketRepository bucketRepository;

    private final BlobRepository blobRepository;

    private final SharedBlobRepository sharedBlobRepository;

    private final IndexBlobService indexBlobService;

    public DefaultStorageService(StorageSystem storageSystem,
                                 InternalBucketRepository bucketRepository,
                                 BlobRepository blobRepository,
                                 SharedBlobRepository sharedBlobRepository,
                                 IndexBlobService indexBlobService) {
        this.storageSystem = storageSystem;
        this.bucketRepository = bucketRepository;
        this.blobRepository = blobRepository;
        this.sharedBlobRepository = sharedBlobRepository;
        this.indexBlobService = indexBlobService;
    }

    @Override
    public Owner createOwner(OwnerType type, String name) {
        return new InternalOwner(type, name);
    }

    @Override
    public Bucket createBucket(String bucketName, Owner owner) {
        return InternalBucket.builder().name(bucketName).owner(owner).build();
    }

    @Override
    public Optional<Bucket> findBucket(String bucketName) {
        return this.bucketRepository.findById(bucketName).map(bucket -> bucket);
    }

    @Override
    public Bucket getBucket(String bucketName) {
        return this.findBucket(bucketName).orElseThrow();
    }

    @Override
    public boolean existsBucket(String bucketName) {
        return this.bucketRepository.existsById(bucketName);
    }

    @Transactional
    @Override
    public Bucket addBucket(Bucket bucket) throws StorageException {
        return this.bucketRepository.save(InternalBucket.of(bucket));
    }

    @Transactional
    @Override
    public void deleteBucket(String bucketName) {
        this.indexBlobService.deleteIndexes(bucketName);
        this.blobRepository.deleteAllByBucket(bucketName);
        this.bucketRepository.deleteById(bucketName);
    }

    @Override
    public Optional<Blob> findBlob(String bucketName, String path) {
        return this.blobRepository.findById(new InternalBlobId(bucketName, path));
    }

    @Override
    public Blob getBlob(String bucketName, String path) {
        return this.findBlob(bucketName, path).orElseThrow();
    }

    @Override
    public BlobQuery createBlobQuery() {
        return new InternalBlobQuery();
    }

    @Override
    public SliceList<Blob> getBlobs(BlobQuery query) {
        return this.blobRepository.findAll(query);
    }

    private void makeDirectories(InternalBlob blob) throws IOException {
        Blob parentBlob = BlobDirectories.getParent(blob);
        if (Objects.nonNull(parentBlob)) {
            if (!this.blobRepository.existsById(InternalBlobId.of(parentBlob.toId()))) {
                storeBlob(parentBlob);
            }
            blob.setParent(InternalBlob.of(parentBlob));
        }
    }

    private boolean existsSharedBlob(SharedBlob sharedBlob) {
        var existsSharedBlob = this.sharedBlobRepository.findByBlob(sharedBlob);
        if (Objects.isNull(existsSharedBlob)) {
            return false;
        }
        sharedBlob.setUrl(existsSharedBlob.getUrl());
        sharedBlob.setSize(existsSharedBlob.getSize());
        return true;
    }

    @Transactional
    @Override
    public Blob storeBlob(Blob blob) throws StorageException, IOException {
        try (InternalBlob internalBlob = InternalBlob.of(blob)) {
            makeDirectories(internalBlob);
            if (BlobType.FILE.equals(blob.getType())) {
                var sharedBlob = SharedBlob.of(internalBlob);
                if (this.existsSharedBlob(sharedBlob)) {
                    internalBlob.setUrl(sharedBlob.getUrl());
                    internalBlob.setSize(sharedBlob.getSize());
                } else {
                    this.storageSystem.storeBlob(internalBlob);
                    sharedBlob.setUrl(internalBlob.getUrl());
                    sharedBlob.setPath(internalBlob.getPath());
                    this.sharedBlobRepository.save(sharedBlob);
                }
            }
            this.indexBlobService.buildIndexes(blob.getBucket(), blob.getPath());
            return this.blobRepository.save(internalBlob);
        }
    }

    @Transactional
    @Override
    public void deleteBlob(String bucketName, String path) {
        List<String> paths = this.indexBlobService.getIndexes(bucketName, path);
        this.blobRepository.deleteAllByBucketAndPaths(bucketName, paths);
        this.indexBlobService.deleteIndexes(bucketName, path);
    }

    @Transactional
    @Override
    public void deleteBlobs(String bucketName, List<String> paths) {
        List<String> indexPaths = this.indexBlobService.getIndexes(bucketName, paths);
        this.blobRepository.deleteAllByBucketAndPaths(bucketName, indexPaths);
        this.indexBlobService.deleteIndexes(bucketName, paths);
    }

}
