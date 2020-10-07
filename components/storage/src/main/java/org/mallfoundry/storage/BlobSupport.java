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
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.util.PathUtils;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BlobSupport implements MutableBlob {

    @Override
    public BlobId toId() {
        return new ImmutableBlobId(this.getBucketId(), this.getId());
    }

    @Override
    public BlobPath toPath() {
        return new ImmutableBlobPath(this.getBucketId(), this.getPath());
    }

    @Override
    public void createNewFile() {
        this.setType(BlobType.FILE);
    }

    @Override
    public void makeDirectory() {
        this.setType(BlobType.DIRECTORY);
    }

    @Override
    public void rename(String name) {
        this.setName(name);
    }

    @Override
    public void moveTo(Blob blob) {
        var name = FilenameUtils.getName(this.getPath());
        this.setPath(Objects.isNull(blob) ? PathUtils.normalize(name) : PathUtils.concat(blob.getPath(), name));
        this.setParent(blob);
    }

    @Override
    public void rebuildIndexes() {
        List<String> indexes = new ArrayList<>();
        String parentPath = PathUtils.normalize(this.getPath());
        while (!PathUtils.isRootPath(parentPath)) {
            parentPath = FilenameUtils.getFullPathNoEndSeparator(parentPath);
            indexes.add(parentPath);
        }
        this.setIndexes(indexes);
    }

    @Override
    public void create() {
        var id = this.getId();
        Assert.notNull(id, "Blob id is required");
        if (StringUtils.isBlank(this.getName())) {
            this.setName(FilenameUtils.getName(this.getPath()));
        }
        var path = FilenameUtils.getPathNoEndSeparator(this.getPath());
        var extension = FilenameUtils.getExtension(this.getPath());
        // File 类型的 Blob 保留后缀名。
        var newFilename = BlobType.FILE.equals(this.getType()) && StringUtils.isNotEmpty(extension)
                ? String.format("%s.%s", id, extension) : id;
        this.setPath(PathUtils.concat(path, newFilename));

        if (StringUtils.isBlank(this.getContentType()) && BlobType.FILE.equals(this.getType())) {
            Optional.ofNullable(this.getPath())
                    .flatMap(MediaTypeFactory::getMediaType)
                    .map(MimeType::toString)
                    .ifPresent(this::setContentType);
        }
        this.rebuildIndexes();
        this.setCreatedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final BlobSupport blob;

        protected BuilderSupport(BlobSupport blob) {
            this.blob = blob;
        }

        @Override
        public Builder id(String id) {
            this.blob.setId(id);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.blob.setName(name);
            return this;
        }

        @Override
        public Builder path(String path) {
            this.blob.setPath(path);
            return this;
        }

        @Override
        public Builder type(BlobType type) {
            this.blob.setType(type);
            return this;
        }

        @Override
        public Builder contentType(String contentType) {
            this.blob.setContentType(contentType);
            return this;
        }

        @Override
        public Blob build() {
            return this.blob;
        }
    }
}
