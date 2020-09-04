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

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.util.PathUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractStorageSystem implements StorageSystem {

    private String baseUrl;

    @Getter
    @Setter
    private String baseDirectory;

    @Setter
    private StoragePathReplacer pathReplacer;

    public void setBaseUrl(String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new StorageException("The base url must not be empty");
        }
        this.baseUrl = Optional.of(baseUrl)
                .map(String::trim)
                .map(s -> s.endsWith("/") ? s : s + "/")
                .orElseThrow();
    }

    public AbstractStorageSystem(String baseUrl) {
        this.setBaseUrl(baseUrl);
    }

    @Override
    public void storeBlob(Blob blob) throws IOException {
        var pathname = this.getStorePath(blob);
        this.storeBlobToPath(blob, pathname);
        if (Objects.isNull(blob.getUrl())) {
            blob.setUrl(this.concatAccessUrl(pathname));
        }
        if (blob.getSize() == 0) {
            blob.setSize(blob.toFile().length());
        }
    }

    protected String getStorePath(Blob blob) throws IOException {
        return Objects.isNull(this.pathReplacer)
                ? PathUtils.concat(blob.getBucketId(), blob.getPath())
                : this.pathReplacer.replace(blob.toFile());
    }

    protected String concatAccessUrl(String path) {
        var url = path.startsWith("/") ? path.substring(1) : path;
        return String.join("", this.baseUrl, url);
    }

    public abstract void storeBlobToPath(Blob blob, String pathname) throws IOException;
}
