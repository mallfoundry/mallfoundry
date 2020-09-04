/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.storage;

import org.apache.commons.io.FileUtils;
import org.mallfoundry.util.PathUtils;

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
        if (BlobType.FILE.equals(blob.getType())) {
            String path = PathUtils.concat(blob.getBucketId(), blob.getPath());
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
