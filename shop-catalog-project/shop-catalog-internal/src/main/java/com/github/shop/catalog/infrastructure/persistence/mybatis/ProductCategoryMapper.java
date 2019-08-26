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

package com.github.shop.catalog.infrastructure.persistence.mybatis;

import com.github.shop.catalog.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductCategoryMapper {

    ProductCategory selectById(@Param("id") long id);

    List<ProductCategory> selectListByLevel(@Param("level") byte level);

    List<ProductCategory> selectListByParentId(@Param("parentId") long parentId);

    void deleteById(@Param("id") long id);

    void update(ProductCategory category);

    void insertCategory(ProductCategory category);

    void insertCategories(@Param("categories") List<ProductCategory> categories);

}
