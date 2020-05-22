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

package com.mallfoundry.rest.store;


import com.mallfoundry.StaticServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class StoreTests {

    @Autowired
    private InternalStoreService storeService;


    @Rollback(false)
    @Transactional
    @Test
    public void testSaveStores() {
        this.storeService.initializeStore(this.storeService.createStore("mi").toBuilder().name("小米官方旗舰店").logoUrl(ofLogoUrl("mi-logo")).build());
        this.storeService.initializeStore(this.storeService.createStore("mi").toBuilder().name("OPPO官方旗舰店").logoUrl(ofLogoUrl("oppo-logo")).build());
        this.storeService.initializeStore(this.storeService.createStore("mi").toBuilder().name("华为官方旗舰店").logoUrl(ofLogoUrl("huawei-logo")).build());
        this.storeService.initializeStore(this.storeService.createStore("mi").toBuilder().name("VIVO官方旗舰店").logoUrl(ofLogoUrl("vivo-logo")).build());
        this.storeService.initializeStore(this.storeService.createStore("mi").toBuilder().name("一加官方旗舰店").logoUrl(ofLogoUrl("oneplus-logo")).build());
    }

    private String ofLogoUrl(String name) {
        return StaticServer.BASE_URL + "/images/" + name + ".jpg";
    }
}
