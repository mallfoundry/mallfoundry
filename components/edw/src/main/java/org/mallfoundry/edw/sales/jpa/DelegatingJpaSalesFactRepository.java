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

package org.mallfoundry.edw.sales.jpa;

import org.mallfoundry.edw.sales.SalesFact;
import org.mallfoundry.edw.sales.SalesFactKey;
import org.mallfoundry.edw.sales.SalesFactRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaSalesFactRepository implements SalesFactRepository {

    private final JpaSalesFactRepository repository;

    public DelegatingJpaSalesFactRepository(JpaSalesFactRepository repository) {
        this.repository = repository;
    }

    @Override
    public SalesFact create(SalesFactKey factKey) {
        return new JpaSalesFact(factKey);
    }

    @Override
    public Optional<SalesFact> findByKey(SalesFactKey factKey) {
        return CastUtils.cast(this.repository.findById(CastUtils.cast(factKey)));
    }

    @Override
    public SalesFact save(SalesFact fact) {
        return this.repository.save(CastUtils.cast(fact));
    }

    @Override
    public List<SalesFact> saveAll(List<SalesFact> facts) {
        return CastUtils.cast(this.repository.saveAll(CastUtils.cast(facts)));
    }
}
