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
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Getter
@Setter
public class FileBlobResource implements BlobResource {

    private String bucketId;

    private String path;

    private String name;

    private BlobType type = BlobType.FILE;

    private String url;

    private long size;

    private String contentType;

    private File file;

    private boolean temporaryFile;

    private File createUrlToTemporaryFile() throws IOException {
        var urlResource = new UrlResource(this.getUrl());
        return this.streamToTemporaryFile(urlResource.getInputStream());
    }

    private File streamToTemporaryFile(InputStream stream) throws IOException {
        var basename = FilenameUtils.getBaseName(this.getPath());
        var temporaryFilePrefix = String.format("%s_%s_", System.currentTimeMillis(), basename);
        var temporaryFileSuffix = String.format(".%s", FilenameUtils.getExtension(this.getPath()));
        File temporaryFile = File.createTempFile(temporaryFilePrefix, temporaryFileSuffix);
        try (stream) {
            FileUtils.copyToFile(stream, temporaryFile);
        }
        this.temporaryFile = true;
        return temporaryFile;
    }

    @Override
    public BlobPath toPath() {
        return new ImmutableBlobPath(this.bucketId, this.path);
    }

    @Override
    public File toFile() throws IOException {
        if (Objects.isNull(this.file) && Objects.nonNull(this.getUrl())) {
            this.file = this.createUrlToTemporaryFile();
        }
        return this.file;
    }

    @Override
    public boolean isReadable() {
        return Objects.nonNull(this.file);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (BlobType.DIRECTORY.equals(this.getType())) {
            throw new IOException("The blob is a directory");
        }
        if (Objects.nonNull(this.file)) {
            return FileUtils.openInputStream(this.file);
        }
        if (Objects.nonNull(this.getUrl())) {
            return new UrlResource(this.getUrl()).getInputStream();
        }
        throw new IOException("The blob has no stream");
    }

    @Override
    public final void close() throws IOException {
        if (this.temporaryFile && this.file.exists()) {
            FileUtils.forceDelete(this.file);
        }
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final FileBlobResource resource;

        protected BuilderSupport(FileBlobResource resource) {
            this.resource = resource;
        }

        @Override
        public Builder bucketId(String bucketId) {
            this.resource.setBucketId(bucketId);
            return this;
        }

        @Override
        public Builder path(String path) {
            this.resource.setPath(path);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.resource.setName(name);
            return this;
        }

        @Override
        public Builder type(BlobType type) {
            this.resource.setType(type);
            return this;
        }

        @Override
        public Builder contentType(String contentType) {
            this.resource.setContentType(contentType);
            return this;
        }

        @Override
        public Builder inputStream(InputStream inputStream) throws IOException {
            return this.file(this.resource.streamToTemporaryFile(inputStream));
        }

        @Override
        public Builder file(File file) {
            this.resource.setFile(file);
            return this;
        }

        @Override
        public BlobResource build() {
            return this.resource;
        }
    }
}
