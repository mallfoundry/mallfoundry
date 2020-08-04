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

package org.mallfoundry.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CollectionTests {

    @Autowired
    private ProductCollectionService collectionService;

    private ProductCollection createCollection(String storeId, String name) {
        return this.collectionService.createCollection(null).toBuilder().storeId(storeId).name(name).build();
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testAdd() {
        var storeId = "mi";
        collectionService.addCollection(this.createCollection(storeId, "手机"));
        collectionService.addCollection(this.createCollection(storeId, "电视/盒子"));
        collectionService.addCollection(this.createCollection(storeId, "笔记本/路由"));
        collectionService.addCollection(this.createCollection(storeId, "智能家电"));
        collectionService.addCollection(this.createCollection(storeId, "儿童智能"));
        collectionService.addCollection(this.createCollection(storeId, "耳机/音箱"));
        collectionService.addCollection(this.createCollection(storeId, "生活/箱包"));
    }

    @Test
    @Transactional
    public void testGetCustomCollection() {
//        List<TopCustomCollection> customCollections =
//                this.customCollectionService.getAllCollections(new StoreId("mi"));
//        System.out.println(customCollections);
    }
}
