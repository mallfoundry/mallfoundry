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

import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.Blob;
import org.mallfoundry.storage.Bucket;
import org.mallfoundry.store.StoreId;

import java.io.IOException;
import java.util.List;

public interface StoreBlobService {

    StoreBlobQuery createStoreBlobQuery();

    Bucket getStoreBucket(StoreId storeId);

    void initializeStoreBucket(StoreId storeId);

    Blob storeStoreBlob(Blob blob) throws IOException, StoreBlobException;

    SliceList<Blob> getStoreBlobs(StoreBlobQuery query);

    void deleteStoreBlob(StoreId storeId, String path);

    void deleteStoreBlobs(StoreId storeId, List<String> paths);

    void clearStoreBlobs(StoreId storeId);
}
