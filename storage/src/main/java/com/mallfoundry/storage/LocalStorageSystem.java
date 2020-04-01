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

import com.mallfoundry.storage.internal.PathUtils;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalStorageSystem implements StorageSystem {

    @Getter
    private StorageConfiguration configuration;

    public LocalStorageSystem(StorageConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        String path = PathUtils.join(blob.getBucket(), blob.getPath());
        File storeFile = new File(PathUtils.join(this.getStoreDirectory(), path));
        if (blob.isFile()) {
            FileUtils.touch(storeFile);
            // close resource.
            try (blob) {
                FileUtils.copyToFile(blob.getInputStream(), storeFile);
            }
            blob.setSize(FileUtils.sizeOf(storeFile));
            blob.setUrl(this.getHttpUrl(path));
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
