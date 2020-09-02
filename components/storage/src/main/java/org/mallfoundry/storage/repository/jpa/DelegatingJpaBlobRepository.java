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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.BlobId;
import org.mallfoundry.storage.BlobQuery;
import org.mallfoundry.storage.BlobRepository;
import org.mallfoundry.storage.InternalBlob;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.Optional;

public class DelegatingJpaBlobRepository implements BlobRepository {

    private final JpaBlobRepository repository;

    public DelegatingJpaBlobRepository(JpaBlobRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(BlobId blobId) {
        return this.repository.existsById(JpaBlobId.of(blobId));
    }

    @Override
    public Optional<Blob> findById(BlobId blobId) {
        return CastUtils.cast(this.repository.findById(JpaBlobId.of(blobId)));
    }

    @Override
    public SliceList<Blob> findAll(BlobQuery blobQuery) {
        return this.repository.findAll(blobQuery);
    }

    @Override
    public Blob save(Blob blob) {
        return this.repository.save(InternalBlob.of(blob));
    }

    @Override
    public void deleteAllByBucketAndPaths(String bucket, Collection<String> paths) {
        this.repository.deleteAllByBucketAndPaths(bucket, paths);
    }

    @Override
    public void deleteAllByBucket(String bucket) {
        this.repository.deleteAllByBucket(bucket);
    }
}
