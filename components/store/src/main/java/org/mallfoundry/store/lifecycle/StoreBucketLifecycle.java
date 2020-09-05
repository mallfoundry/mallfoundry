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

package org.mallfoundry.store.lifecycle;

import org.mallfoundry.storage.BucketId;
import org.mallfoundry.storage.MediaBucket;
import org.mallfoundry.storage.StorageService;
import org.mallfoundry.store.Store;
import org.mallfoundry.util.QualifiedCodes;

public class StoreBucketLifecycle implements StoreLifecycle {

    private final StorageService storageService;

    public StoreBucketLifecycle(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public void doInitialize(Store store) {
        var imageBucketId = this.createImageBucketId(store.getId());
        var imageBucket = this.storageService.createBucket(imageBucketId).toBuilder().name("Store Image").build();
        this.storageService.addBucket(imageBucket);
    }

    @Override
    public void doClose(Store store) {
        var imageBucketId = this.createImageBucketId(store.getId());
        this.storageService.deleteBucket(imageBucketId);
    }

    @Override
    public int getPosition() {
        return 0;
    }

    private BucketId createImageBucketId(String storeId) {
        return this.storageService.createBucketId(QualifiedCodes.STORE_TYPE_CODE + MediaBucket.IMAGE.code() + storeId);
    }
}
