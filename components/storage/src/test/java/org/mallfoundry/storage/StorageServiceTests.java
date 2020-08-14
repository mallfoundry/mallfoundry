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

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.storage.acl.OwnerType;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@StandaloneTest
public class StorageServiceTests {

    @Autowired
    private StorageService storageService;


    @Test
    public void testIsDirectory() {
        File file = new File("D:\\apps\\build");
        System.out.println(file.getName());
    }

    @Test
    public void testGetBucket() {
        this.storageService.getBucket("test")
                .ifPresent(System.out::println);
    }

    @Test
    public void testAddBucket() {
        var owner = this.storageService.createOwner(OwnerType.STORE, "mi");
        var bucket = this.storageService.createBucket("test", owner);
        if (!this.storageService.existsBucket("test")) {
            Bucket newBucket = this.storageService.addBucket(bucket);
            System.out.println(newBucket);
        }
    }

    @Test
    @Transactional
    public void testGetBlob() throws IOException {
        this.storageService.getBlob("test", "/abc3/test2/c.txt")
                .ifPresent(blob -> {
                    System.out.println(blob);
                    Blob parent = blob.getParent();
                    System.out.println(parent.toId());
                });
    }

    @Test
    public void testStoreBlob() throws IOException {
        Bucket bucket = this.storageService.getBucket("test").orElseThrow();
        this.storageService.storeBlob(bucket.createBlob("/abc/test2.txt", FileUtils.openInputStream(new File("D:\\adb.txt"))));
        this.storageService.storeBlob(bucket.createBlob("/abc/test3.txt", FileUtils.openInputStream(new File("D:\\adb.txt"))));
    }

    @Test
    public void testStoreBlob2() throws IOException {
        Bucket bucket = this.storageService.getBucket("test").orElseThrow();
        Blob blob = bucket.createBlob("/abc2/test_dir3");
        Blob storeBlob = this.storageService.storeBlob(blob);
        System.out.println(storeBlob);
    }

    @Test
    public void testStoreBlob3() throws IOException {
        Bucket bucket = this.storageService.getBucket("test").orElseThrow();
        this.storageService.storeBlob(bucket.createBlob("/abc2/test2/a.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test2/b.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test2/c.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test2/d.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test3/a.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test3/b.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test3/c.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc2/test3/d.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test2/a.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test2/b.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test2/c.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test2/d.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test3/a.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test3/b.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test3/c.txt"));
        this.storageService.storeBlob(bucket.createBlob("/abc3/test3/d.txt"));
    }

    @Test
    @Transactional
    public void testStoreBlobs() throws IOException {
        SliceList<Blob> pageBlobs = this.storageService
                .getBlobs(this.storageService
                        .createBlobQuery()
                        .toBuilder()
                        .bucket("test")
                        .path("/abc3/test2")
                        .page(1)
                        .limit(10).build());
        System.out.println(pageBlobs);
    }

    @Test
    @Rollback(false)
    @Transactional
    public void testDeleteBlob() {
        this.storageService.deleteBlob("store-mi", "/images");
    }
}
