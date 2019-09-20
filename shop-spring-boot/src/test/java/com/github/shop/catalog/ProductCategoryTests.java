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

package com.github.shop.catalog;

import com.github.shop.util.JsonUtils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductCategoryTests {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void testGetTopList() throws Exception {

        List<ProductCategory> productCategories = productCategoryRepository.findTopList();
        System.out.println(productCategories);
        String productJsonString = JsonUtils.stringify(productCategories);
        System.out.println(productJsonString);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testCreate() throws Exception {
        ProductCategory category1 = new ProductCategory();
        category1.setLevel(ProductCategory.TOP_LEVEL);
        category1.setName("男装");
        category1.setFirstLetter("N");
        category1.setIndex((short) 1);
        ProductCategory category2 = new ProductCategory();
        category2.setLevel(ProductCategory.TOP_LEVEL);
        category2.setName("女装");
        category2.setFirstLetter("N");
        category2.setIndex((short) 2);
        productCategoryRepository.create(List.of(category1, category2));
    }


}
