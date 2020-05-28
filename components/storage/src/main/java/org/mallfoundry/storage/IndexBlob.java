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
