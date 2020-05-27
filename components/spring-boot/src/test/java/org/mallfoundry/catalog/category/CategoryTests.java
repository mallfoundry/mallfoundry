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

package org.mallfoundry.catalog.category;

import org.mallfoundry.catalog.InternalCategory;
import org.mallfoundry.catalog.InternalCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CategoryTests {

    @Autowired
    private InternalCategoryService categoryService;

    @Transactional
    @Rollback(false)
    @Test
    public void testAdd() {
//        this.categoryService.createTopCategory("test111");
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testGetCategory() {
//        InternalCategory category = this.categoryService.getCategory(732);
//        System.out.println(category);
    }


    @Transactional
    @Rollback(false)
    @Test
    public void testLocalCategoriesToRepository() throws Exception {

//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource resource = resolver.getResource("classpath:/categories.json");
//        String jsonString = IOUtils.toString(resource.getURI(), StandardCharsets.UTF_8);
//
//        List<ChildCategory> categories = JsonUtils.parse(jsonString, List.class, ChildCategory.class);
//        for (ChildCategory category1 : categories) {
//            Category topCategory = this.categoryService.createTopCategory(category1.getName());
//
//            for (ChildCategory category2 : category1.getChildren()) {
//                ChildCategory category22 = topCategory.createChildCategory(category2.getName());
//                topCategory.addChildCategory(category22);
//
//                for (ChildCategory category3 : category2.getChildren()) {
//                    ChildCategory category33 = category22.createChildCategory(category3.getName());
//                    category33.setIcon(category3.getIcon());
//                    category33.set(category3.getKeywords());
//                    category22.addChildCategory(category33);
//                }
//            }
//        }
    }
}
