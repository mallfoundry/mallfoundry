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
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_storage_index_blob")
public class IndexBlob implements Serializable {

    @EmbeddedId
    private IndexBlobId id;

    public IndexBlob(String bucket, String path, String value) {
        this.id = new IndexBlobId(bucket, path, value);
    }

    public String getValue() {
        return this.id.getValue();
    }

    public String getPath() {
        return this.id.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IndexBlob that = (IndexBlob) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static List<IndexBlob> of(String bucket, String path) {
        return resolveIndexes(path)
                .stream()
                .map(aPath -> new IndexBlob(bucket, path, aPath))
                .collect(Collectors.toList());
    }

    private static List<String> resolveIndexes(String path) {
        List<String> indexes = new ArrayList<>();
        String parentPath = PathUtils.normalize(path);
        while (!PathUtils.isRootPath(parentPath)) {
            parentPath = PathUtils.getParentPath(parentPath);
            indexes.add(parentPath);
        }
        return indexes;
    }
}
