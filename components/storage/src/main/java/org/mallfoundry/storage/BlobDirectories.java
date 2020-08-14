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

import org.apache.commons.io.FilenameUtils;

import java.util.Objects;

public abstract class BlobDirectories {

    public static Blob getParent(Blob blob) {
        if (Objects.nonNull(blob.getParent())) {
            return blob.getParent();
        }
        if (PathUtils.isRootPath(blob.getPath())) {
            return null;
        }
        String path =
                blob.getPath().endsWith(PathUtils.PATH_SEPARATOR)
                        ? FilenameUtils.getFullPathNoEndSeparator(blob.getPath())
                        : blob.getPath();

        String parentPath = FilenameUtils.getFullPathNoEndSeparator(path);
        if (PathUtils.isRootPath(parentPath)) {
            return null;
        }
        var parent = new InternalBlob(new InternalBlobId(blob.getBucket(), parentPath));
        parent.createDirectory();
        return parent;
    }

}
