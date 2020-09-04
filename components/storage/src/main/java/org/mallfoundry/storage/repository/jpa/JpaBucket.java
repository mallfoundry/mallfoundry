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
import org.mallfoundry.storage.Bucket;
import org.mallfoundry.storage.BucketId;
import org.mallfoundry.storage.BucketSupport;
import org.mallfoundry.storage.ImmutableBlobPath;
import org.mallfoundry.storage.acl.InternalOwner;
import org.mallfoundry.storage.acl.Owner;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_storage_bucket")
public class JpaBucket extends BucketSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Embedded
    private InternalOwner owner;

    @Override
    public void setOwner(Owner owner) {
        this.owner = InternalOwner.of(owner);
    }

    public JpaBucket(BucketId bucketId) {
        this.setId(bucketId.getId());
    }

    public static JpaBucket of(Bucket bucket) {
        var target = new JpaBucket();
        BeanUtils.copyProperties(bucket, target);
        return target;
    }

    @Override
    public Blob createBlob(String path, File file) {
        return new JpaBlob(new ImmutableBlobPath(this.getId(), path), file);
    }

    @Override
    public Blob createBlob(String path, InputStream inputStream) throws IOException {
        return new JpaBlob(new ImmutableBlobPath(this.getId(), path), inputStream);
    }

    @Override
    public Blob createBlob(String path) {
        return new JpaBlob(new ImmutableBlobPath(this.getId(), path));
    }
}
