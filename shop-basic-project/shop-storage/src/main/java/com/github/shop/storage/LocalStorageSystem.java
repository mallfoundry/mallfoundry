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

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

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
        String path = PathUtils.join(resource.getBucket(), resource.getPath());
        File storeFile = new File(PathUtils.join(this.getStoreDirectory(), path));
        FileUtils.touch(storeFile);
        try (resource) {
            FileUtils.copyToFile(resource.getInputStream(), storeFile);
        }
        StorageObject storageObject = new StorageObject();
        storageObject.setBucket(resource.getBucket());
        storageObject.setPath(resource.getPath());
        storageObject.setLength(storeFile.length());
        storageObject.setUrl(this.getHttpUrl(path));
        MediaType mediaType = MediaTypeFactory.getMediaType(resource.getPath()).orElse(MediaType.ALL);
        storageObject.setContentType(mediaType.toString());
        return storageObject;
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
