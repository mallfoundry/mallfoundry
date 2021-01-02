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


import org.mallfoundry.catalog.repository.jpa.JpaBrand;
import org.mallfoundry.data.SliceList;
import org.springframework.transaction.annotation.Transactional;

public class DefaultBrandService implements BrandService {

    private final BrandRepository brandRepository;

    public DefaultBrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand createBrand(String id) {
        return new JpaBrand(id);
    }

    @Override
    public BrandQuery createBrandQuery() {
        return new DefaultBrandQuery();
    }

    @Transactional
    @Override
    public Brand addBrand(Brand brand) throws BrandException {
        if (this.brandRepository.existsById(brand.getId())) {
            throw new BrandException("The brand id already exists");
        }
        return this.brandRepository.save(JpaBrand.of(brand));
    }

    @Override
    public Brand getBrand(String id) {
        return this.brandRepository.findById(id)
                .orElseThrow();
    }

    private Brand requiredBrand(String id) {
        return this.brandRepository.findById(id).orElseThrow();
    }

    @Override
    public SliceList<Brand> getBrands(BrandQuery query) {
        return this.brandRepository.findAll(query);
    }

    @Transactional
    @Override
    public Brand updateBrand(Brand source) {
        var brand = this.requiredBrand(source.getId());
        return this.brandRepository.save(brand);
    }

    @Transactional
    @Override
    public void deleteBrand(String id) {
        var brand = this.requiredBrand(id);
        this.brandRepository.delete(brand);
    }
}
