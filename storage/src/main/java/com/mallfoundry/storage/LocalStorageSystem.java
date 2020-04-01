/*
 * Copyright 2019 the original author or authors.
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

import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LocalStorageSystem implements StorageSystem {

    private final SharedBlobRepository sharedBlobRepository;

    @Getter
    private final StorageConfiguration configuration;

    public LocalStorageSystem(SharedBlobRepository sharedBlobRepository,
                              StorageConfiguration configuration) {
        this.sharedBlobRepository = sharedBlobRepository;
        this.configuration = configuration;
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        String path = PathUtils.join(blob.getBucket(), blob.getPath());
        if (blob.isFile()) {
            try (var sharedBlob = SharedBlob.of(blob)) {
                var existsSharedBlob = this.sharedBlobRepository.findByBlob(sharedBlob);
                if (Objects.isNull(existsSharedBlob)) {
                    File storeFile = new File(PathUtils.join(this.getStoreDirectory(), path));
                    FileUtils.touch(storeFile);
                    FileUtils.copyFile(sharedBlob.getFile(), storeFile);
                    sharedBlob.setUrl(this.getHttpUrl(path));
                    this.sharedBlobRepository.save(sharedBlob);
                    blob.setUrl(sharedBlob.getUrl());
                    blob.setSize(sharedBlob.getSize());
                } else {
                    sharedBlob.setUrl(existsSharedBlob.getUrl());
                    sharedBlob.setPath(existsSharedBlob.getPath());
                }

                blob.setUrl(sharedBlob.getUrl());
                blob.setSize(sharedBlob.getSize());
            }
        }
    }

    @Override
    public void deleteObject(String bucket, String path) {
        FileUtils.deleteQuietly(new File(PathUtils.join(this.getStoreDirectory(), bucket, path)));
    }

    private String getStoreDirectory() {
        return this.getConfiguration().getStore().getDirectory();
    }

    private String getHttpUrl(String path) {
        String baseUrl = this.getConfiguration().getHttp().getBaseUrl();
        return baseUrl + "/" + path;
    }
}
