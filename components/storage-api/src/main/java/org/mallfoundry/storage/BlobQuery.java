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

import org.mallfoundry.data.Pageable;

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
