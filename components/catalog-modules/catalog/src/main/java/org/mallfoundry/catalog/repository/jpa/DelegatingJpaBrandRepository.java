/*
 * Copyright (C) 2019-2021 the original author or authors.
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

package org.mallfoundry.catalog.repository.jpa;

import org.mallfoundry.catalog.Brand;
import org.mallfoundry.catalog.BrandQuery;
import org.mallfoundry.catalog.BrandRepository;
import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaBrandRepository implements BrandRepository {

    private final JpaBrandRepository repository;

    public DelegatingJpaBrandRepository(JpaBrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public Brand save(Brand brand) {
        return this.repository.save(JpaBrand.of(brand));
    }

    @Override
    public boolean existsById(String brandId) {
        return this.repository.existsById(brandId);
    }

    @Override
    public Optional<Brand> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public SliceList<Brand> findAll(BrandQuery query) {
        return CastUtils.cast(this.repository.findAll(query));
    }

    @Override
    public void delete(Brand brand) {
        this.repository.delete(JpaBrand.of(brand));
    }
}
