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

package org.mallfoundry.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CollectionTests {

    @Autowired
    private CollectionService collectionService;

    @Transactional
    @Rollback(false)
    @Test
    public void testAdd() {
        var storeId = "mi";
        collectionService.addCollection(collectionService.createCollection(storeId, "手机"));
        collectionService.addCollection(collectionService.createCollection(storeId, "电视/盒子"));
        collectionService.addCollection(collectionService.createCollection(storeId, "笔记本/路由"));
        collectionService.addCollection(collectionService.createCollection(storeId, "智能家电"));
        collectionService.addCollection(collectionService.createCollection(storeId, "儿童智能"));
        collectionService.addCollection(collectionService.createCollection(storeId, "耳机/音箱"));
        collectionService.addCollection(collectionService.createCollection(storeId, "生活/箱包"));
    }

    @Test
    @Transactional
    public void testGetCustomCollection() {
//        List<TopCustomCollection> customCollections =
//                this.customCollectionService.getAllCollections(new StoreId("mi"));
//        System.out.println(customCollections);
    }
}
