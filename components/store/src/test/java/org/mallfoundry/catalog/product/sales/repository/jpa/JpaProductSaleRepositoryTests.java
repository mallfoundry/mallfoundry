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

package org.mallfoundry.catalog.product.sales.repository.jpa;

import org.junit.jupiter.api.Test;
import org.mallfoundry.catalog.product.sales.DefaultProductSaleQuery;
import org.mallfoundry.test.StandaloneTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@StandaloneTest
public class JpaProductSaleRepositoryTests {

    @Autowired
    private DelegatingJpaProductSaleRepository repository;

    @Transactional
    @Test
    public void testSumQuantitiesPrintSql() {
        var endDate = LocalDate.now();
        var startDate = endDate.minusDays(30);
        // start date
        var yearStart = (short) startDate.getYear();
        var monthStart = (byte) startDate.getMonthValue();
        var dayOfMonthStart = (byte) startDate.getDayOfMonth();
        // end date
        var yearEnd = (short) endDate.getYear();
        var monthEnd = (byte) endDate.getMonthValue();
        var dayOfMonthEnd = (byte) endDate.getDayOfMonth();
        var query = new DefaultProductSaleQuery()
                .toBuilder()
                .yearStart(yearStart).monthStart(monthStart).dayOfMonthStart(dayOfMonthStart)
                .yearEnd(yearEnd).monthEnd(monthEnd).dayOfMonthEnd(dayOfMonthEnd)
                .build();
        query.setProductId("10");
        this.repository.sumQuantities(query);
    }
}
