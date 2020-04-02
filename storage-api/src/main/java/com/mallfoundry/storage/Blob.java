/*
 * Copyright 2020 the original author or authors.
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

import java.io.Closeable;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

public interface Blob extends Serializable, Closeable {

    BlobId getBlobId();

    void setBlobId(BlobId blobId);

    String getBucket();

    void setBucket(String bucket);

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

    InputStream getInputStream() throws StorageException;

    Map<String, String> getMetadata();

    void setMetadata(Map<String, String> metadata);

    void createFile();

    void createDirectory();

    void rename(String name);

    Builder toBuilder();

    class Builder {

        private final Blob blob;

        public Builder(Blob blob) {
            this.blob = blob;
        }

        public Builder directory() {
            this.blob.createDirectory();
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
