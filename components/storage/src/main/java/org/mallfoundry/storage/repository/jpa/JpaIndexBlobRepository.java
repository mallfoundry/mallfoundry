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

import org.mallfoundry.storage.IndexBlob;
import org.mallfoundry.storage.IndexBlobId;
import org.mallfoundry.storage.IndexBlobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaIndexBlobRepository
        extends IndexBlobRepository, JpaRepository<IndexBlob, IndexBlobId> {

    @Query("from IndexBlob where id.bucket = ?1 and (id.path = ?2 or id.value = ?2)")
    @Override
    List<IndexBlob> findAllByBucketAndPath(String bucket, String path);

    @Query("from IndexBlob where id.bucket = ?1 and (id.path in (?2) or id.value in (?2))")
    @Override
    List<IndexBlob> findAllByBucketAndPaths(String bucket, List<String> path);

    @Modifying
    @Query("delete from IndexBlob where id.bucket = ?1 and id.path in (?2)")
    @Override
    void deleteAllByBucketAndPaths(String bucket, List<String> paths);

    @Modifying
    @Query("delete from IndexBlob where id.bucket = ?1")
    @Override
    void deleteAllByBucket(String bucket);
}
