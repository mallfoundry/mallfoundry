/*
 * Copyright 2019 the original author or authors.
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

package com.github.shop.storage;

import com.github.shop.storage.LocalStorageSystem;
import com.github.shop.storage.ObjectResource;
import com.github.shop.storage.StorageConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;

public class LocalStorageSystemTests {

    @Test
    public void testStoreObject() throws Exception {

        StorageConfiguration configuration = new StorageConfiguration();
        configuration.getStore().setDirectory("D:/local_data");
        configuration.getHttp().setBaseUrl("http://localhost:8081/static");
        LocalStorageSystem lss = new LocalStorageSystem(configuration);
        ObjectResource resource = new ObjectResource("test_b", new File("D:/img2.jpg"));
        StorageObject storageObject = lss.storeObject(resource);
        System.out.println(storageObject);
    }

    @Test
    public void tesDeleteObject() throws Exception {

        StorageConfiguration configuration = new StorageConfiguration();
        configuration.getStore().setDirectory("D:/local_data");
        LocalStorageSystem lss = new LocalStorageSystem(configuration);
        lss.deleteObject("test_b", "img2.jpg");
    }
}
