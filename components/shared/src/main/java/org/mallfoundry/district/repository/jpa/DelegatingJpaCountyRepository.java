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

package org.mallfoundry.district.repository.jpa;

import org.mallfoundry.district.County;
import org.mallfoundry.district.CountyRepository;
import org.mallfoundry.district.DistrictQuery;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaCountyRepository implements CountyRepository {

    private final JpaCountyRepository repository;

    public DelegatingJpaCountyRepository(JpaCountyRepository repository) {
        this.repository = repository;
    }

    @Override
    public County create(String id) {
        return new JpaCounty(id);
    }

    @Override
    public County save(County county) {
        return this.repository.save(JpaCounty.of(county));
    }

    @Override
    public List<County> findAll(DistrictQuery query) {
        return CastUtils.cast(this.repository.findAll(query));
    }

    @Override
    public Optional<County> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

}
