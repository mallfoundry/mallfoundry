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

package com.mallfoundry.storage;

import com.mallfoundry.data.SliceList;
import com.mallfoundry.storage.acl.InternalOwner;
import com.mallfoundry.storage.acl.Owner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalStorageService implements StorageService {

    private final StorageSystem storageSystem;

    private final InternalBucketRepository bucketRepository;

    private final InternalBlobRepository blobRepository;

    public InternalStorageService(StorageSystem storageSystem,
                                  InternalBucketRepository bucketRepository,
                                  InternalBlobRepository blobRepository) {
        this.storageSystem = storageSystem;
        this.bucketRepository = bucketRepository;
        this.blobRepository = blobRepository;
    }

    @Override
    public Owner createOwner(String type, String name) {
        return new InternalOwner(type, name);
    }

    @Override
    public Bucket createBucket(String bucketName, Owner owner) {
        return InternalBucket.builder().name(bucketName).owner(owner).build();
    }

    @Override
    public Optional<Bucket> getBucket(String bucketName) {
        return this.bucketRepository.findById(bucketName).map(bucket -> bucket);
    }

    @Override
    public boolean existsBucket(String bucketName) {
        return this.bucketRepository.existsById(bucketName);
    }

    @Transactional
    @Override
    public Bucket saveBucket(Bucket bucket) throws StorageException {
        return this.bucketRepository.save(InternalBucket.of(bucket));
    }

    @Override
    public void deleteBucket(String bucketName) {
        this.bucketRepository.deleteById(bucketName);
    }

    @Override
    public Optional<Blob> getBlob(String bucketName, String path) {
        return this.blobRepository
                .findById(new InternalBlobId(bucketName, path))
                .map(blob -> blob);
    }

    @Override
    public BlobQuery createBlobQuery() {
        return new InternalBlobQuery();
    }

    @Override
    public SliceList<Blob> getBlobs(BlobQuery query) {
        return this.blobRepository.findAll(query);
    }

    private void makeDirectories(InternalBlob blob) {
        Blob parentBlob = BlobDirectories.getParent(blob);
        if (Objects.nonNull(parentBlob)) {
            if (!this.blobRepository.existsById(InternalBlobId.of(parentBlob.getBlobId()))) {
                storeBlob(parentBlob);
            }
            blob.setParent(InternalBlob.of(parentBlob));
        }
    }

    @Transactional
    @Override
    public Blob storeBlob(Blob blob) throws StorageException {
        InternalBlob internalBlob = InternalBlob.of(blob);
        makeDirectories(internalBlob);

        try {
            storageSystem.storeBlob(internalBlob);
        } catch (IOException e) {
            throw new StorageException(e);
        }
        return this.blobRepository.save(internalBlob);
    }

    @Transactional
    @Override
    public void deleteBlob(String bucketName, String path) {
        this.blobRepository.findById(new InternalBlobId(bucketName, path))
                .ifPresent(blob -> {
                    List<InternalBlob> blobs = this.blobRepository.findAllByBucketAndIndexes(bucketName, List.of(path));
                    blobs.add(blob);
                    this.blobRepository.deleteAll(blobs);
                });
    }
}
