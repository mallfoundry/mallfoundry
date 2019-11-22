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

import com.mallfoundry.store.application.CustomCollectionService;
import com.mallfoundry.store.domain.product.CustomCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomCollectionTests {

    @Autowired
    private CustomCollectionService customCollectionService;


    @Test
    public void testAdd() {
        CustomCollection collection1 = new CustomCollection();
        collection1.setName("collection_1");
        collection1.setStoreId("store_1");
        collection1.setEnabled(true);
        this.customCollectionService.addCollection(collection1);
    }
}
