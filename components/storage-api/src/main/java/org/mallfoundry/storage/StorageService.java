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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StorageService {

    Owner createOwner(OwnerType type, String name);

    Bucket createBucket(String bucketName, Owner owner);

    Optional<Bucket> getBucket(String bucketName);

    boolean existsBucket(String bucketName);

    Bucket addBucket(Bucket bucket) throws StorageException;

    void deleteBucket(String bucketName);

    Blob storeBlob(Blob blob) throws StorageException, IOException;

    void deleteBlob(String bucketName, String path);

    void deleteBlobs(String bucketName, List<String> paths);

    BlobQuery createBlobQuery();

    Optional<Blob> getBlob(String bucketName, String path);

    SliceList<Blob> getBlobs(BlobQuery query);
}
