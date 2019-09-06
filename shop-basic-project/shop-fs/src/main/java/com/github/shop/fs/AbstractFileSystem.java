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

package com.github.shop.fs;

import com.github.shop.fs.store.StoreFileInfo;

import java.io.IOException;

public abstract class AbstractFileSystem implements FileSystem {

    private final FileSystemConfiguration configuration;

    private final StoreFileRepository storeFileRepository;


    public AbstractFileSystem(FileSystemConfiguration configuration,
                              StoreFileRepository storeFileRepository) {
        this.configuration = configuration;
        this.storeFileRepository = storeFileRepository;
    }

    @Override
    public FileSystemConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public FileInfo store(FileResource resource) throws IOException {
        // close resource
        try (FileResource res = resource) {
            StoreFileInfo fileInfo = this.doStore(resource);
            this.storeFileRepository.save(fileInfo);
            return fileInfo;
        }
    }

    protected abstract StoreFileInfo doStore(FileResource resource) throws IOException;
}
