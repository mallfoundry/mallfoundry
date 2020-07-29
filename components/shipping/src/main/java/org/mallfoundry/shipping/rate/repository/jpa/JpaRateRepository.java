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

package org.mallfoundry.shipping.rate.repository.jpa;

import org.mallfoundry.data.SliceList;
import org.mallfoundry.shipping.rate.Rate;
import org.mallfoundry.shipping.rate.RateQuery;
import org.mallfoundry.shipping.rate.RateRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRateRepository implements RateRepository {

    private final JpaRateRepositoryDelegate repository;

    public JpaRateRepository(JpaRateRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public Rate create(String id) {
        return new JpaRate(id);
    }

    @Override
    public Rate save(Rate rate) {
        return this.repository.save(JpaRate.of(rate));
    }

    @Override
    public Optional<Rate> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public SliceList<Rate> findAll(RateQuery query) {
        return null;
    }

    @Override
    public void delete(Rate rate) {
        this.repository.delete(JpaRate.of(rate));
    }
}
