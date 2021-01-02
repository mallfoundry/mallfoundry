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

package org.mallfoundry.catalog;

import org.mallfoundry.catalog.repository.jpa.JpaCategory;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

    private static final String CATALOG_CATEGORY_ID_VALUE_NAME = "catalog.category.id";

    private final CategoryRepository categoryRepository;

    private final ApplicationEventPublisher eventPublisher;

    public DefaultCategoryService(CategoryRepository categoryRepository,
                                  ApplicationEventPublisher eventPublisher) {
        this.categoryRepository = categoryRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Category createCategory(String id) {
        return new JpaCategory(id);
    }

    @Override
    public CategoryQuery createCategoryQuery() {
        return new DefaultCategoryQuery();
    }

    @Override
    public Category getCategory(String id) {
        return this.categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> getCategories(CategoryQuery query) {
        return CastUtils.cast(this.categoryRepository.findAll(query));
    }

    @Override
    public Category addCategory(Category category) {
        category.setId(PrimaryKeyHolder.next(CATALOG_CATEGORY_ID_VALUE_NAME));
        return this.categoryRepository.save(JpaCategory.of(category));
    }

    @Transactional
    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(JpaCategory.of(category));
    }

    @Transactional
    @Override
    public Category addCategory(String id, Category category) {
        this.getCategory(id).addCategory(category);
        return category;
    }

    @Transactional
    @Override
    public void deleteCategory(String categoryId) {
        var category = this.categoryRepository.findById(categoryId).orElseThrow();
        this.eventPublisher.publishEvent(new ImmutableCategoryDeletedEvent(category));
        this.categoryRepository.delete(category);
    }

}
