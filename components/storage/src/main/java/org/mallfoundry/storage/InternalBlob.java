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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.data.repository.jpa.convert.StringStringMapConverter;
import org.mallfoundry.util.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.MimeType;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_storage_blob")
@IdClass(InternalBlobId.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InternalBlob implements Blob {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @Column(name = "bucket_")
    @Setter(AccessLevel.PRIVATE)
    private String bucket;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @Column(name = "path_")
    private String path;

    @Column(name = "name_")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_")
    private BlobType type;

    @Column(name = "url_")
    private String url;

    @Column(name = "size_")
    private long size;

    @Column(name = "content_type_")
    private String contentType;

    @Lob
    @Column(name = "metadata_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> metadata = new HashMap<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private InternalBlob parent;

    @JsonIgnore
    @Transient
    private File file;

    @JsonIgnore
    @Transient
    private boolean temporaryFile;

    public InternalBlob(BlobId blobId) {
        this.setBlobId(InternalBlobId.of(blobId));
        this.createDirectory();
    }

    public InternalBlob(BlobId blobId, File file) {
        this.setBlobId(InternalBlobId.of(blobId));
        this.setFile(file);
        this.createFile();
    }

    public InternalBlob(BlobId blobId, InputStream inputStream) throws IOException {
        this.setBlobId(InternalBlobId.of(blobId));
        this.setFile(this.streamToTemporaryFile(inputStream));
        this.createFile();
    }

    public static InternalBlob of(Blob blob) throws IOException {
        if (blob instanceof InternalBlob) {
            return (InternalBlob) blob;
        }

        var internalBlob = new InternalBlob();
        if (blob.isDirectory()) {
            BeanUtils.copyProperties(blob, internalBlob, "file");
        } else {
            BeanUtils.copyProperties(blob, internalBlob);
            internalBlob.setFile(blob.toFile());
        }
        return internalBlob;
    }

    @JsonIgnore
    @Override
    public BlobId getBlobId() {
        return new InternalBlobId(this.bucket, this.path);
    }

    @Override
    public void setBlobId(BlobId blobId) {
        this.setBucket(blobId.getBucket());
        this.setPath(blobId.getPath());
    }

    private void setPath(String path) {
        this.path = PathUtils.normalize(path);
    }

    @Override
    public String getName() {
        return StringUtils.isEmpty(this.name)
                ? FilenameUtils.getName(this.getPath())
                : this.name;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isDirectory() {
        return BlobType.DIRECTORY == this.type;
    }

    @JsonIgnore
    @Transient
    @Override
    public boolean isFile() {
        return BlobType.FILE == this.type;
    }

    @Override
    public Blob getParent() {
        return this.parent;
    }

    @Override
    public InputStream openInputStream() throws StorageException, IOException {
        if (this.isDirectory()) {
            throw new StorageException("The blob is a directory");
        }

        if (Objects.nonNull(this.file)) {
            return FileUtils.openInputStream(this.file);
        }

        if (Objects.nonNull(this.url)) {
            return new UrlResource(this.url).getInputStream();
        }
        throw new StorageException("The blob has no stream. ");
    }

    public String getContentType() {
        if (StringUtils.isNotEmpty(this.contentType)) {
            return this.contentType;
        }
        return Optional.ofNullable(this.getName())
                .flatMap(MediaTypeFactory::getMediaType)
                .map(MimeType::toString)
                .orElse(null);
    }

    @Override
    public File toFile() throws IOException {
        if (Objects.isNull(this.file) && Objects.nonNull(this.url)) {
            this.file = this.createUrlToTemporaryFile();
        }
        return this.file;
    }

    private File createUrlToTemporaryFile() throws IOException {
        var urlResource = new UrlResource(this.getUrl());
        return this.streamToTemporaryFile(urlResource.getInputStream());
    }

    private File streamToTemporaryFile(InputStream stream) throws IOException {
        var temporaryFileSuffix = String.format(".%s", FilenameUtils.getExtension(this.getName()));
        File temporaryFile =
                File.createTempFile(
                        String.format("%s_%s", System.currentTimeMillis(),
                                FilenameUtils.getBaseName(this.getName())), temporaryFileSuffix);
        try (stream) {
            FileUtils.copyToFile(stream, temporaryFile);
        }
        this.temporaryFile = true;
        return temporaryFile;
    }

    @Override
    public void createFile() {
        this.setType(BlobType.FILE);
    }

    @Override
    public void createDirectory() {
        this.setType(BlobType.DIRECTORY);
    }

    @Override
    public void rename(String name) {
        this.setName(name);
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void close() throws IOException {
        if (this.temporaryFile && this.file.exists()) {
            FileUtils.forceDelete(this.file);
        }
    }
}
