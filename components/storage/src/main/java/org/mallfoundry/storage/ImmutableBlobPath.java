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
import org.apache.commons.io.FilenameUtils;
import org.mallfoundry.util.PathUtils;

import java.util.Objects;

@Getter
public class ImmutableBlobPath implements BlobPath {
    private final String bucketId;
    private final String id;
    // Current path value.
    private final String path;

    public ImmutableBlobPath(String bucketId, String path) {
        this.bucketId = bucketId;
        this.path = path;
        this.id = FilenameUtils.getName(path);
    }

    @Override
    public BlobId toId() {
        return new ImmutableBlobId(this.bucketId, this.id);
    }

    @Override
    public BlobPath getParent() {
        var pp = FilenameUtils.getFullPathNoEndSeparator(this.path);
        return Objects.isNull(pp) || PathUtils.isRootPath(pp) ? null : new ImmutableBlobPath(this.bucketId, pp);
    }

    @Override
    public String toString() {
        return this.path;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImmutableBlobPath)) {
            return false;
        }
        ImmutableBlobPath that = (ImmutableBlobPath) object;
        return Objects.equals(bucketId, that.bucketId) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketId, path);
    }
}
