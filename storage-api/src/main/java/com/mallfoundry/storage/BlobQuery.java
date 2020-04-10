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

import com.mallfoundry.data.Pageable;

public interface BlobQuery extends Pageable {

    BlobType getType();

    void setType(BlobType type);

    String getBucket();

    void setBucket(String bucket);

    String getPath();

    void setPath(String path);

    Builder toBuilder();

    class Builder {
        private BlobQuery query;

        public Builder(BlobQuery query) {
            this.query = query;
        }

        public Builder page(int page) {
            this.query.setPage(page);
            return this;
        }

        public Builder limit(int limit) {
            this.query.setLimit(limit);
            return this;
        }

        public Builder file() {
            this.query.setType(BlobType.FILE);
            return this;
        }

        public Builder directory() {
            this.query.setType(BlobType.DIRECTORY);
            return this;
        }

        public Builder type(BlobType type) {
            this.query.setType(type);
            return this;
        }

        public Builder bucket(String bucket) {
            this.query.setBucket(bucket);
            return this;
        }

        public Builder path(String path) {
            this.query.setPath(path);
            return this;
        }

        public BlobQuery build() {
            return this.query;
        }
    }
}
