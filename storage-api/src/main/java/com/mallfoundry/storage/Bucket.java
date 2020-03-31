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

import com.mallfoundry.storage.acl.Owner;

import java.io.IOException;
import java.io.InputStream;

public interface Bucket {

    Owner getOwner();

    void setOwner(Owner owner);

    String getName();

    void setName(String name);

    Blob createBlob(String path, InputStream inputStream) throws IOException;

    Blob createBlob(String path);

    class Builder {
        private Bucket bucket;

        public Builder(Bucket bucket) {
            this.bucket = bucket;
        }

        public Builder name(String name) {
            this.bucket.setName(name);
            return this;
        }

        public Builder owner(Owner owner) {
            this.bucket.setOwner(owner);
            return this;
        }

        public Bucket build() {
            return this.bucket;
        }
    }
}
