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
import com.mallfoundry.storage.acl.Owner;

import java.io.IOException;
import java.util.Optional;

public interface StorageService {

    Owner createOwner(String type, String name);

    Bucket createBucket(String bucketName, Owner owner);

    Optional<Bucket> getBucket(String bucketName);

    boolean existsBucket(String bucketName);

    Bucket saveBucket(Bucket bucket) throws StorageException;

    void deleteBucket(String bucketName);

    Blob storeBlob(Blob blob) throws StorageException, IOException;

    void deleteBlob(String bucketName, String path);

    BlobQuery createBlobQuery();

    Optional<Blob> getBlob(String bucketName, String path);

    SliceList<Blob> getBlobs(BlobQuery query);
}
