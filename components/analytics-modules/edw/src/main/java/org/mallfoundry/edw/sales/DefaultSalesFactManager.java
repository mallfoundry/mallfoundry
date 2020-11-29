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

package org.mallfoundry.edw.sales;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultSalesFactManager implements SalesFactManager {

    private final SalesFactRepository salesFactRepository;

    public DefaultSalesFactManager(SalesFactRepository salesFactRepository) {
        this.salesFactRepository = salesFactRepository;
    }

    @Override
    public SalesFactKey createSalesFactKey() {
        return new ImmutableSalesFactKey();
    }

    @Override
    public SalesFact createSalesFact(SalesFactKey factKey) {
        return this.salesFactRepository.create(factKey);
    }

    private SalesFact getSalesFact(SalesFactKey key) {
        return this.salesFactRepository.findByKey(key)
                .orElseGet(() -> this.salesFactRepository.create(key));
    }

    @Transactional
    @Override
    public SalesFact saveSalesFact(SalesFact source) {
        var fact = this.getSalesFact(source.toKey());
        fact.adjustSalesQuantity(source.getSalesQuantity());
        fact.adjustSalesAmount(source.getSalesAmount());
        return this.salesFactRepository.save(fact);
    }

    @Transactional
    @Override
    public List<SalesFact> saveSalesFacts(List<SalesFact> facts) {
        return facts.stream().map(this::saveSalesFact).collect(Collectors.toUnmodifiableList());
    }
}
