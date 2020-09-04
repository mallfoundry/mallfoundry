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

package org.mallfoundry.storage.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobPath;
import org.mallfoundry.storage.BlobType;
import org.mallfoundry.storage.FileBlob;
import org.springframework.beans.BeanUtils;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_storage_blob")
public class JpaBlob extends FileBlob {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "bucket_id_")
    private String bucketId;

    @Column(name = "path_")
    private String path;

    @Column(name = "name_")
    private String name;

    @Column(name = "type_")
    private BlobType type = BlobType.FILE;

    @Column(name = "url_")
    private String url;

    @Column(name = "size_")
    private long size;

    @Column(name = "content_type_")
    private String contentType;

    @ElementCollection
    @CollectionTable(name = "mf_storage_index_blob",
            joinColumns = @JoinColumn(name = "blob_id_", referencedColumnName = "id_"))
    @Column(name = "value_")
    private List<String> indexes = new ArrayList<>();

    @OneToOne(targetEntity = JpaBlob.class, fetch = FetchType.LAZY)
    private Blob parent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaBlob(BlobPath blobPath) {
        this.setBucketId(blobPath.getBucketId());
        this.setPath(blobPath.toString());
        this.makeDirectory();
    }

    public JpaBlob(BlobPath blobPath, File file) {
        super(blobPath, file);
    }

    public JpaBlob(BlobPath blobPath, InputStream inputStream) throws IOException {
        super(blobPath, inputStream);
    }

    public static JpaBlob of(Blob blob) {
        if (blob instanceof JpaBlob) {
            return (JpaBlob) blob;
        }
        var internalBlob = new JpaBlob();
        if (BlobType.DIRECTORY.equals(blob.getType())) {
            BeanUtils.copyProperties(blob, internalBlob, "file");
        } else {
            BeanUtils.copyProperties(blob, internalBlob);
            try {
                internalBlob.setFile(blob.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return internalBlob;
    }
}
