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

import org.mallfoundry.storage.acl.Owner;

public abstract class BucketSupport implements Bucket {

    @Override
    public Bucket.Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final BucketSupport bucket;

        public BuilderSupport(BucketSupport bucket) {
            this.bucket = bucket;
        }

        public Bucket.Builder name(String name) {
            this.bucket.setName(name);
            return this;
        }

        public Bucket.Builder owner(Owner owner) {
            this.bucket.setOwner(owner);
            return this;
        }

        public Bucket build() {
            return this.bucket;
        }
    }
}
