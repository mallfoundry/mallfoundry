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
import org.mallfoundry.storage.BlobPath;
import org.mallfoundry.storage.BlobQuery;
import org.mallfoundry.storage.BlobRepository;
import org.springframework.data.util.CastUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DelegatingJpaBlobRepository implements BlobRepository {

    private final JpaBlobRepository repository;

    public DelegatingJpaBlobRepository(JpaBlobRepository repository) {
        this.repository = repository;
    }

    @Override
    public Blob create(BlobPath path) {
        return new JpaBlob(path);
    }

    @Override
    public Blob save(Blob blob) {
        return this.repository.save(JpaBlob.of(blob));
    }

    @Override
    public Optional<Blob> findById(BlobId blobId) {
        return CastUtils.cast(this.repository.findById(blobId.getId()));
    }

    @Override
    public List<Blob> findAllById(Collection<BlobId> blobIds) {
        return CastUtils.cast(
                this.repository.findAllById(
                        blobIds.stream().map(BlobId::getId).collect(Collectors.toUnmodifiableSet())));
    }

    @Override
    public SliceList<Blob> findAll(BlobQuery blobQuery) {
        return this.repository.findAll(blobQuery);
    }

    @Override
    public void delete(Blob blob) {
        this.repository.deleteAllByIdInOrIndexesIn(Set.of(blob.getId()), Set.of(blob.getPath()));
    }

    @Override
    public void deleteAll(List<Blob> blobs) {
        var ids = new HashSet<String>();
        var paths = new HashSet<String>();
        for (var blob : blobs) {
            ids.add(blob.getId());
            paths.add(blob.getPath());
        }
        this.repository.deleteAllByIdInOrIndexesIn(ids, paths);
    }

    @Override
    public void deleteAllByBucketId(String bucket) {
        this.repository.deleteAllByBucketId(bucket);
    }
}
