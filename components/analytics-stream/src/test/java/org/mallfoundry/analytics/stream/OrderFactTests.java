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

package org.mallfoundry.analytics.stream;

import org.junit.jupiter.api.Test;
import org.mallfoundry.analytics.stream.order.OrderItemFactRepository;
import org.mallfoundry.analytics.stream.order.OrderQuantityFactRepository;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@StandaloneTest
public class OrderFactTests {

    @Autowired
    private OrderItemFactRepository repository;

    @Autowired
    private OrderQuantityFactRepository quantityRepository;

    @Transactional
    @Rollback(false)
    @Test
    public void testCountAll() {
        var ids = IntStream.rangeClosed(405, 408)
                .mapToObj(Integer::toString)
                .collect(Collectors.toUnmodifiableSet());
        var items = this.repository.findAllById(ids);
        var quantities = this.repository.countAll(items);
        this.quantityRepository.saveAll(quantities);
    }
}
