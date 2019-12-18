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

package com.mallfoundry.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomCollectionTests {

    @Autowired
    private CustomCollectionService customCollectionService;

    @Transactional
    @Rollback(false)
    @Test
    public void testAdd() {
        StoreId storeId = new StoreId("mi");

        // mobile
        CustomCollection mobile = customCollectionService.createTopCollection(storeId, "手机");
        mobile.addChildCollection(new ChildCustomCollection("小米CC9 Pro"));
        mobile.addChildCollection(new ChildCustomCollection("小米CC9e"));
        mobile.addChildCollection(new ChildCustomCollection("小米CC9"));
        mobile.addChildCollection(new ChildCustomCollection("小米9"));
        mobile.addChildCollection(new ChildCustomCollection("小米9 Pro"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi K30"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi Note8 Pro"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi Note8"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi K20 Pro 尊享版"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi 8A"));
        mobile.addChildCollection(new ChildCustomCollection("Redmi 8"));

        // TV
        CustomCollection tv = customCollectionService.createTopCollection(storeId, "电视/盒子");
        tv.addChildCollection(new ChildCustomCollection("小米电视4C 55英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4A 43英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4A 65英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视E65A英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4A 32英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4A 55英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4C 43英寸"));
        tv.addChildCollection(new ChildCustomCollection("小米电视全面屏"));
        tv.addChildCollection(new ChildCustomCollection("小米电视4S 65英寸"));

        // notebook
        CustomCollection notebook = customCollectionService.createTopCollection(storeId, "笔记本/路由");
        notebook.addChildCollection(new ChildCustomCollection("Air 12.5寸"));
        notebook.addChildCollection(new ChildCustomCollection("Air 13.3寸"));
        notebook.addChildCollection(new ChildCustomCollection("Redmi Book 14寸"));
        notebook.addChildCollection(new ChildCustomCollection("Pro 15.6寸"));
        notebook.addChildCollection(new ChildCustomCollection("游戏本"));
        notebook.addChildCollection(new ChildCustomCollection("Ruby系列"));
        notebook.addChildCollection(new ChildCustomCollection("显示器"));
        notebook.addChildCollection(new ChildCustomCollection("小米路由器4A 千兆"));
        notebook.addChildCollection(new ChildCustomCollection("小米路由器4A"));
        notebook.addChildCollection(new ChildCustomCollection("小米路由器4"));
        notebook.addChildCollection(new ChildCustomCollection("小米路由器Pro"));
        notebook.addChildCollection(new ChildCustomCollection("小米路由器HD"));

        // HA
        CustomCollection ha = customCollectionService.createTopCollection(storeId, "智能家电");
        ha.addChildCollection(new ChildCustomCollection("米家1级能效变频空调"));
        ha.addChildCollection(new ChildCustomCollection("米家互联网空调1匹"));
        ha.addChildCollection(new ChildCustomCollection("米家大一匹空调"));
        ha.addChildCollection(new ChildCustomCollection("米家互联网空调C1"));
        ha.addChildCollection(new ChildCustomCollection("米家互联网空调C2"));
        ha.addChildCollection(new ChildCustomCollection("米家洗烘一体机"));
        ha.addChildCollection(new ChildCustomCollection("米家洗烘一体机1A"));
        ha.addChildCollection(new ChildCustomCollection("Redmi全自动波轮洗衣机"));
        ha.addChildCollection(new ChildCustomCollection("米家互联网烟灶"));

        // CI
        customCollectionService.createTopCollection(storeId, "儿童智能");
        customCollectionService.createTopCollection(storeId, "耳机/音箱");
        customCollectionService.createTopCollection(storeId, "生活/箱包");
    }


    @Test
    @Transactional
    public void testGetCustomCollection() {
        List<TopCustomCollection> customCollections =
                this.customCollectionService.getAllCollections(new StoreId("mi"));
        System.out.println(customCollections);
    }
}
