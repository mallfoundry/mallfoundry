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

package org.mallfoundry.store.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.StoreIndustry;
import org.mallfoundry.store.StoreIndustryId;
import org.mallfoundry.store.StoreIndustryQuery;
import org.mallfoundry.store.StoreIndustryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaStoreIndustryRepository implements StoreIndustryRepository {

    private final JpaStoreIndustryRepository repository;

    public DelegatingJpaStoreIndustryRepository(JpaStoreIndustryRepository repository) {
        this.repository = repository;
    }

    @Override
    public StoreIndustry create(StoreIndustryId industryId) {
        return new JpaStoreIndustry(industryId);
    }

    @Override
    public Optional<StoreIndustry> findById(StoreIndustryId industryId) {
        return CastUtils.cast(this.repository.findById(industryId.getId()));
    }

    @Override
    public SliceList<StoreIndustry> findAll(StoreIndustryQuery query) {
        var page = this.repository.findAllByTenantId(query.getTenantId(),
                PageRequest.of(query.getPage() - 1, query.getLimit()));
        return PageList.of(page);
    }

    @Override
    public StoreIndustry save(StoreIndustry industry) {
        return CastUtils.cast(this.repository.save(JpaStoreIndustry.of(industry)));
    }

    @Override
    public void delete(StoreIndustry industry) {
        this.repository.delete(JpaStoreIndustry.of(industry));
    }
}
