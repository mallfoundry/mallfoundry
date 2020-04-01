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

import java.util.List;
import java.util.Optional;

public interface InternalBlobRepository {

    Optional<InternalBlob> findById(InternalBlobId blobId);

    List<InternalBlob> findAllByBucketAndIndexes(String bucket, List<String> indexes);

    boolean existsById(InternalBlobId blobId);

    InternalBlob save(InternalBlob blob);

    void deleteAll(Iterable<? extends InternalBlob> entities);

    SliceList<Blob> findAll(BlobQuery blobQuery);

}
