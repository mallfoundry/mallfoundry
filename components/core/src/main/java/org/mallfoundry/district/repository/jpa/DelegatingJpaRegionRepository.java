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

import org.mallfoundry.district.Region;
import org.mallfoundry.district.RegionRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;

public class DelegatingJpaRegionRepository implements RegionRepository {

    private final JpaRegionRepository repository;

    public DelegatingJpaRegionRepository(JpaRegionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Region create(String id) {
        return new JpaRegion(id);
    }

    @Override
    public Region save(Region region) {
        return this.repository.save(JpaRegion.of(region));
    }

    @Override
    public List<Region> findAllByCountryId(String provinceId) {
        return CastUtils.cast(this.repository.findAllByCountryId(provinceId));
    }
}
