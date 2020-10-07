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

import org.junit.jupiter.api.Test;
import org.mallfoundry.storage.acl.InternalOwner;
import org.mallfoundry.storage.acl.OwnerType;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

@StandaloneTest
public class StorageServiceTests {

    @Autowired
    private StorageService storageService;

    @Test
    @Rollback(false)
    public void testAddBucket() {
        var bucketId = this.storageService.createBucketId(null);
        var bucket = this.storageService.createBucket(bucketId);
        bucket.setOwner(new InternalOwner(OwnerType.STORE, "1"));
        bucket.setName("Test Bucket");
        this.storageService.addBucket(bucket);
    }

    @Test
    @Rollback(false)
    public void testStoreBlob() throws FileNotFoundException {
        var file = ResourceUtils.getFile("classpath:e523be52552921bf.jpg");
        var resource = this.storageService.createBlobResource().toBuilder()
                .bucketId("10410132")
                .path("/kerxpee6lfltg92w/abc.jpg")
                .file(file)
                .build();
        this.storageService.storeBlob(resource);
    }

    private void storeBlob(String path) throws IOException {
       /* var bucketId = this.storageService.createBucketId("1f08eb45142b47e8964ad12625d8f73e");
        var bucket = this.storageService.createBucket(bucketId);
        var blob = bucket.createBlob(path, new File(""));
        this.storageService.storeBlob(blob);*/
    }

    @Test
    @Rollback(false)
    public void testStoreBlob2() throws IOException {
        this.storeBlob("/990d5276367740c8b95128a6c80201f4/abc.jpg");
        this.storeBlob("/990d5276367740c8b95128a6c80201f4/abc2.jpg");
        this.storeBlob("/990d5276367740c8b95128a6c80201f4/abc3.jpg");
        this.storeBlob("/990d5276367740c8b95128a6c80201f4/abc4.jpg");
    }

    @Transactional
    @Test
    public void testGetStoreBlobs() {
        var query = this.storageService.createBlobQuery().toBuilder().bucketId("1f08eb45142b47e8964ad12625d8f73e").build();
        var list = this.storageService.getBlobs(query);
        System.out.println(list);
    }

}
