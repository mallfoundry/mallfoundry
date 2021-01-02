/*
 * Copyright (C) 2019-2021 the original author or authors.
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

package org.mallfoundry.autoconfigure.catalog;

import org.mallfoundry.catalog.CategoryRepository;
import org.mallfoundry.catalog.DefaultCategoryService;
import org.mallfoundry.catalog.repository.jpa.DelegatingJpaCategoryRepository;
import org.mallfoundry.catalog.repository.jpa.JpaCategoryRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryAutoConfiguration {

    @Bean
    public DelegatingJpaCategoryRepository delegatingJpaCategoryRepository(JpaCategoryRepository repository) {
        return new DelegatingJpaCategoryRepository(repository);
    }

    @Bean
    public DefaultCategoryService defaultCategoryService(CategoryRepository categoryRepository,
                                                         ApplicationEventPublisher eventPublisher) {
        return new DefaultCategoryService(categoryRepository, eventPublisher);
    }
}
