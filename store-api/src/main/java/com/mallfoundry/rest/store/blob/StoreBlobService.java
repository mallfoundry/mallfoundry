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
import com.mallfoundry.storage.Blob;
import com.mallfoundry.storage.BlobQuery;
import com.mallfoundry.storage.Bucket;
import com.mallfoundry.rest.store.StoreId;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StoreBlobService {

    BlobQuery createBlobQuery();

    String getBucketName(StoreId storeId);

    Optional<Bucket> getBucket(StoreId storeId);

    void initializeBucket(StoreId storeId);

    Blob storeBlob(Blob blob) throws IOException, StoreBlobException;

    void deleteBlob(StoreId storeId, String path);

    void deleteBlobs(StoreId storeId, List<String> paths);

    SliceList<Blob> getBlobs(BlobQuery query);

}
