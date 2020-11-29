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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.acl.Owner;
import org.mallfoundry.storage.acl.OwnerType;

import java.util.Set;

public interface StorageService {

    Owner createOwner(OwnerType type, String name);

    BucketId createBucketId(String id);

    Bucket createBucket(BucketId bucketId);

    Bucket getBucket(BucketId bucketId) throws BucketException;

    Bucket addBucket(Bucket bucket) throws BucketException;

    void deleteBucket(BucketId bucketId) throws BucketException;

    BlobResource createBlobResource();

    BlobId createBlobId(String bucketId, String blobId);

    BlobQuery createBlobQuery();

    Blob createBlob(BlobId blobId);

    Blob storeBlob(BlobResource resource) throws StorageException;

    SliceList<Blob> getBlobs(BlobQuery query);

    Blob updateBlob(Blob blob);

    void deleteBlob(BlobId blobId);

    void deleteBlobs(Set<BlobId> blobIds);
}
