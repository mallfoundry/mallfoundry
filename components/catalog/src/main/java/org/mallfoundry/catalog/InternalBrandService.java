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

package org.mallfoundry.catalog;


import org.mallfoundry.data.SliceList;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternalBrandService implements BrandService {

    private final BrandRepository brandRepository;

    public InternalBrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandId createBrandId(String id) {
        return new InternalBrandId(id);
    }

    @Override
    public Brand createBrand(String id) {
        return new InternalBrand(id);
    }

    @Override
    public BrandQuery createBrandQuery() {
        return new InternalBrandQuery();
    }

    @Override
    public Brand addBrand(Brand brand) throws BrandException {
        if (this.brandRepository.existsById(brand.getId())) {
            throw new BrandException("The brand id already exists");
        }
        return this.brandRepository.save(InternalBrand.of(brand));
    }

    @Override
    public Optional<Brand> getBrand(String id) {
        return Optional.empty();
    }

    @Override
    public SliceList<Brand> getBrands(BrandQuery query) {
        return CastUtils.cast(this.brandRepository.findAll(query));
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return null;
    }

    @Override
    public void deleteBrand(String id) {

    }
}
