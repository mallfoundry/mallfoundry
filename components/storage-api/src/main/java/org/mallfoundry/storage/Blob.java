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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface Blob extends Serializable, Closeable {

    BlobId toId();

    String getBucket();

    String getPath();

    BlobType getType();

    String getName();

    boolean isDirectory();

    boolean isFile();

    String getUrl();

    void setUrl(String url);

    Blob getParent();

    long getSize();

    void setSize(long size);

    String getContentType();

    void setContentType(String contentType);

    File toFile() throws IOException;

    InputStream openInputStream() throws StorageException, IOException;

    /*Map<String, String> getMetadata();

    void setMetadata(Map<String, String> metadata);*/

    void createFile();

    void makeDirectory();

    void rename(String name);

    Builder toBuilder();

    class Builder {

        private final Blob blob;

        public Builder(Blob blob) {
            this.blob = blob;
        }

        public Builder makeDirectory() {
            this.blob.makeDirectory();
            return this;
        }

        public Builder name(String name) {
            this.blob.rename(name);
            return this;
        }

        public Blob build() {
            return this.blob;
        }
    }
}
