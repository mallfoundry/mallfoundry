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

package com.github.shop.storage;

import com.github.shop.storage.ObjectResource;
import com.github.shop.storage.PathUtils;
import com.github.shop.storage.StorageConfiguration;
import com.github.shop.storage.StorageObject;
import com.github.shop.storage.StorageSystem;
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
    public StorageObject storeObject(ObjectResource resource) throws IOException {
        File storeFile = new File(PathUtils.join(this.getStoreDirectory(), resource.getBucket(), resource.getPath()));
        FileUtils.touch(storeFile);
        try (resource) {
            FileUtils.copyToFile(resource.getInputStream(), storeFile);
        }
        StorageObject storageObject = new StorageObject();
        storageObject.setPath(resource.getPath());
        storageObject.setLength(storeFile.length());
        return storageObject;
    }

    @Override
    public void deleteObject(String bucket, String path) {
        FileUtils.deleteQuietly(new File(PathUtils.join(this.getStoreDirectory(), bucket, path)));
    }

    private String getStoreDirectory() {
        return this.getConfiguration().getStore().getDirectory();
    }
}
