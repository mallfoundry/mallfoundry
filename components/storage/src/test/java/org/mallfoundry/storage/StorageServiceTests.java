/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mallfoundry.storage;

import org.mallfoundry.data.SliceList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@SpringBootTest
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
        var owner = this.storageService.createOwner("store", "mi");
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
                    System.out.println(parent.getBlobId());
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
