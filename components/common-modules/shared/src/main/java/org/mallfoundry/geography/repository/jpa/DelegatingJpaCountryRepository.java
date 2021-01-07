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

package org.mallfoundry.geography.repository.jpa;

import org.mallfoundry.geography.Country;
import org.mallfoundry.geography.CountryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.CastUtils;

import java.util.List;

public class DelegatingJpaCountryRepository implements CountryRepository {

    private final JpaCountryRepository repository;

    public DelegatingJpaCountryRepository(JpaCountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Country create(String id) {
        return new JpaCountry(id);
    }

    @Override
    public Country save(Country country) {
        return this.repository.save(JpaCountry.of(country));
    }

    @Override
    public List<Country> findAll() {
        return CastUtils.cast(this.repository.findAll(Sort.by("position")));
    }
}
