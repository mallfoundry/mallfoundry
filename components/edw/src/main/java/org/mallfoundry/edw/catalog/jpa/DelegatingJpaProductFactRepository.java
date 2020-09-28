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

package org.mallfoundry.edw.catalog.jpa;

import org.mallfoundry.edw.catalog.ProductFact;
import org.mallfoundry.edw.catalog.ProductFactKey;
import org.mallfoundry.edw.catalog.ProductFactRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaProductFactRepository implements ProductFactRepository {

    private final JpaProductFactRepository repository;

    public DelegatingJpaProductFactRepository(JpaProductFactRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductFact create(ProductFactKey factKey) {
        return new JpaProductFact(factKey);
    }

    @Override
    public Optional<ProductFact> findByKey(ProductFactKey factKey) {
        return CastUtils.cast(this.repository.findById(factKey.getVariantKey()));
    }

    @Override
    public ProductFact save(ProductFact fact) {
        return this.repository.save((JpaProductFact) fact);
    }

    @Override
    public List<ProductFact> saveAll(List<ProductFact> facts) {
        return CastUtils.cast(this.repository.saveAll(CastUtils.cast(facts)));
    }
}
