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

import com.github.shop.fs.store.FileStorePathGenerator;
import com.github.shop.fs.store.StoreFileInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

public class LocalFileSystem extends AbstractFileSystem {

    private final FileStorePathGenerator fileStorePathGenerator;

    public LocalFileSystem(FileSystemConfiguration configuration,
                           StoreFileRepository storeFileRepository,
                           FileStorePathGenerator fileStorePathGenerator) {
        super(configuration, storeFileRepository);
        this.fileStorePathGenerator = fileStorePathGenerator;
    }

    private String getAbsoluteLocalPath(String path) {
        return Paths.get(this.getStoreDirectory(), path).toString();
    }

    private String getStoreDirectory() {
        FileSystemConfiguration.Store store = this.getConfiguration().getStore();
        Assert.notNull(store, "Property 'store' is required");
        String dir = store.getDirectory();
        Assert.notNull(dir, "Property 'store.directory' is required");
        return dir;
    }

    @Override
    protected StoreFileInfo doStore(FileResource resource) throws IOException {
        String filename = resource.getFilename();
        StringBuilder newFilenameBuilder = new StringBuilder(UUID.randomUUID().toString());
        String fileExtension = FilenameUtils.getExtension(filename);
        if (!StringUtils.isEmpty(fileExtension)) {
            newFilenameBuilder.append(".").append(fileExtension);
        }
        String newFilename = newFilenameBuilder.toString();
        String relativeFilePath = this.fileStorePathGenerator.storePath(this, newFilename);
        File localFile = new File(this.getAbsoluteLocalPath(relativeFilePath));
        // Copy file data.
        FileUtils.touch(localFile);
        FileUtils.copyToFile(resource.getInputStream(), localFile);
        StoreFileInfo fileInfo = new StoreFileInfo();
        fileInfo.setPath(newFilename);
        fileInfo.setStorePath(relativeFilePath);
        return fileInfo;
    }

    @Override
    public FileInfo get(String filepath) {
        return null;
    }

    @Override
    public FileResource open(String filepath) {
        return null;
    }

    @Override
    public boolean delete(String filepath) {
        return false;
    }

    @Override
    public long sizeOfDirectory(String filepath) {
        return FileUtils.sizeOfDirectory(FileUtils.getFile(this.getAbsoluteLocalPath(filepath)));
    }
}
