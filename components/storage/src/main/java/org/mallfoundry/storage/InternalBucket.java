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
import lombok.Setter;
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
@Entity
@Table(name = "mf_storage_bucket")
public class InternalBucket implements Bucket {

    @Id
    @Column(name = "name_")
    private String name;

    @Embedded
    private InternalOwner owner;

    @Override
    public void setOwner(Owner owner) {
        this.owner = InternalOwner.of(owner);
    }

    @Override
    public Blob createBlob(String path, File file) {
        return new InternalBlob(new InternalBlobId(this.getName(), path), file);
    }

    public static InternalBucket of(Bucket bucket) {
        var target = new InternalBucket();
        BeanUtils.copyProperties(bucket, target);
        return target;
    }

    @Override
    public Blob createBlob(String path, InputStream inputStream) throws IOException {
        return new InternalBlob(new InternalBlobId(this.getName(), path), inputStream);
    }

    @Override
    public Blob createBlob(String path) {
        return new InternalBlob(new InternalBlobId(this.getName(), path));
    }

    public static Builder builder() {
        return new Builder(new InternalBucket());
    }

}
