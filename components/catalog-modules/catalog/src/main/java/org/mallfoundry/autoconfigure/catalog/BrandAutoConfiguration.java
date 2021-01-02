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

import org.mallfoundry.catalog.BrandRepository;
import org.mallfoundry.catalog.DefaultBrandService;
import org.mallfoundry.catalog.repository.jpa.DelegatingJpaBrandRepository;
import org.mallfoundry.catalog.repository.jpa.JpaBrandRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrandAutoConfiguration {

    @Bean
    public DelegatingJpaBrandRepository delegatingJpaBrandRepository(JpaBrandRepository repository) {
        return new DelegatingJpaBrandRepository(repository);
    }

    @Bean
    public DefaultBrandService defaultBrandService(BrandRepository brandRepository) {
        return new DefaultBrandService(brandRepository);
    }
}
