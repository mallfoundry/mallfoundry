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

package org.mallfoundry.store.blob;

import org.mallfoundry.storage.StorageService;
import org.mallfoundry.storage.acl.OwnerType;
import org.mallfoundry.store.StoreId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultStoreBlobService implements StoreBlobService {

    private final StorageService storageService;

    public DefaultStoreBlobService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Transactional
    @Override
    public void initializeBuckets(StoreId storeId) {
        var owner = this.storageService.createOwner(OwnerType.STORE, storeId.getId());
        var bucketId = this.storageService.createBucketId(null);
        var bucket = this.storageService.createBucket(bucketId);
        bucket.setOwner(owner);
        this.storageService.addBucket(bucket);
    }

    @Override
    public void clearStoreBlobs(StoreId storeId) {
//        this.storageService.deleteBucket(this.getStoreBucketName(storeId));
    }
}
