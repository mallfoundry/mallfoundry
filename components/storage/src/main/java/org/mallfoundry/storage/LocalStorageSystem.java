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

package org.mallfoundry.storage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalStorageSystem implements StorageSystem {

    private final String directory;

    private final String baseUrl;

    public LocalStorageSystem(String directory, String baseUrl) {
        this.directory = directory;
        this.baseUrl = baseUrl;
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        if (blob.isFile()) {
            String path = PathUtils.concat(blob.getBucket(), blob.getPath());
            File storeFile = new File(PathUtils.concat(this.directory, path));
            FileUtils.touch(storeFile);
            FileUtils.copyFile(blob.toFile(), storeFile);
            blob.setUrl(this.getHttpUrl(path));
            blob.setSize(storeFile.length());
        }
    }

    private String getHttpUrl(String path) {
        return this.baseUrl + path;
    }
}
