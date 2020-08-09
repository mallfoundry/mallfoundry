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

import org.mallfoundry.district.City;
import org.mallfoundry.district.CityRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;

public class DelegatingJpaCityRepository implements CityRepository {

    private final JpaCityRepository repository;

    public DelegatingJpaCityRepository(JpaCityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City create(String id) {
        return new JpaCity(id);
    }

    @Override
    public City save(City city) {
        return this.repository.save(JpaCity.of(city));
    }

    @Override
    public List<City> findAllByProvinceId(String provinceId) {
        return CastUtils.cast(this.repository.findAllByProvinceId(provinceId));
    }
}
