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

package org.mallfoundry.catalog.product.repository.elasticsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mallfoundry.test.StandaloneTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;

@StandaloneTest
public class ElasticsearchProductRepositoryTests {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ElasticsearchProductRepository repository;

    @Test
    public void testFindAllById() {
        var list = this.repository.findAllById(Set.of("286", "287"));
        Assertions.assertNotNull(list);
        logger.debug(Objects.toString(list));
    }
}
