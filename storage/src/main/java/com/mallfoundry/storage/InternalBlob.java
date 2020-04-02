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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallfoundry.data.jpa.convert.StringStringMapConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.MimeType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "storage_blob")
@IdClass(InternalBlobId.class)
public class InternalBlob implements Blob {

    @Id
    @Column(name = "bucket_")
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

    @JsonProperty("content_type")
    @Column(name = "content_type_")
    private String contentType;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "path_", referencedColumnName = "path_"),
            @JoinColumn(name = "bucket_", referencedColumnName = "bucket_")
    })
    private List<InternalBlobIndex> indexes = new ArrayList<>();

    @Lob
    @Column(name = "metadata_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> metadata = new HashMap<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private InternalBlob parent;

    @JsonIgnore
    @Transient
    private InputStream inputStream;

    public InternalBlob(BlobId blobId) {
        this.setBlobId(InternalBlobId.of(blobId));
        this.createDirectory();
    }

    public InternalBlob(BlobId blobId, File file) throws IOException {
        this(blobId, FileUtils.openInputStream(file));
    }

    public InternalBlob(BlobId blobId, InputStream inputStream) {
        this.setBlobId(InternalBlobId.of(blobId));
        this.setInputStream(inputStream);
        this.createFile();
    }

    public static InternalBlob of(Blob blob) {
        var internalBlob = new InternalBlob();
        BeanUtils.copyProperties(blob, internalBlob);
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

    public void setPath(String path) {
        this.path = PathUtils.normalize(path);
        this.resolveIndexes();
    }

    @Override
    public String getName() {
        return StringUtils.isEmpty(this.name) ?
                FilenameUtils.getName(this.getPath()) : this.name;
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
    public InputStream getInputStream() throws StorageException {
        if (Objects.nonNull(this.inputStream)) {
            return this.inputStream;
        }

        if (Objects.nonNull(this.url)) {
            try {
                return new UrlResource(this.url).getInputStream();
            } catch (Exception e) {
                throw new StorageException(e);
            }
        }

        return null;
    }

    private void resolveIndexes() {
        this.indexes =
                PathUtils.resolveIndexes(this.getPath())
                        .stream().map(index -> new InternalBlobIndex(this.getBucket(), this.getPath(), index))
                        .collect(Collectors.toList());
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
        if (Objects.nonNull(this.inputStream)) {
            this.inputStream.close();
        }
    }
}
