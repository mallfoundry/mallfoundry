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

package com.github.shop.fs.store;

import com.github.shop.fs.FilePathUtils;
import com.github.shop.fs.FileResource;
import com.github.shop.fs.FileSystemConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class LocalStoreFileSystem extends AbstractStoreFileSystem {

    private final StoreFilePathGenerator storeFilePathGenerator;

    public LocalStoreFileSystem(FileSystemConfiguration configuration,
                                StoreFilePathGenerator storeFilePathGenerator) {
        super(configuration);
        this.storeFilePathGenerator = storeFilePathGenerator;
    }

    @Override
    public StoreFile store(FileResource resource) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString();
        StringBuilder newFilePathBuilder =
                new StringBuilder("/").append(this.storeFilePathGenerator.storePath(uniqueFilename));
        String fileExtension = FilenameUtils.getExtension(resource.getFilename());
        if (StringUtils.isNotEmpty(fileExtension)) {
            newFilePathBuilder.append('.').append(fileExtension);
        }

        // new filename
        String newFilePath = newFilePathBuilder.toString();
        String storeFilePath = FilePathUtils.join(this.getStoreDirectory(), newFilePath);
        File storeFile = new File(storeFilePath);
        FileUtils.touch(storeFile);
        FileUtils.copyToFile(resource.getInputStream(), storeFile);

        return new StoreFile(this.getHttpUrl(newFilePath), storeFile.length());
    }

    private String getHttpUrl(String filePath) {
        String baseUrl = this.getConfiguration().getHttp().getBaseUrl();
        StringBuilder httpUrlBuilder = new StringBuilder(baseUrl);
        if (!baseUrl.endsWith("/")) {
            httpUrlBuilder.append("/");
        }

        httpUrlBuilder.append(filePath.startsWith("/") ? filePath.substring(1) : filePath);
        return httpUrlBuilder.toString();
    }

    private String getStoreDirectory() {
        return this.getConfiguration().getStore().getDirectory();
    }
}
