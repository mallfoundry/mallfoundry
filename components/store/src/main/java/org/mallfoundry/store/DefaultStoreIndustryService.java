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

package org.mallfoundry.store;

import org.mallfoundry.data.SliceList;
import org.springframework.transaction.annotation.Transactional;

public class DefaultStoreIndustryService implements StoreIndustryService {

    private final StoreIndustryRepository storeIndustryRepository;

    public DefaultStoreIndustryService(StoreIndustryRepository storeIndustryRepository) {
        this.storeIndustryRepository = storeIndustryRepository;
    }

    @Override
    public StoreIndustryQuery createStoreIndustryQuery() {
        return new DefaultStoreIndustryQuery();
    }

    @Override
    public StoreIndustryId createStoreIndustryId(String tenantId, String industryId) {
        return new ImmutableStoreIndustryId(tenantId, industryId);
    }

    @Override
    public StoreIndustry createStoreIndustry(StoreIndustryId industryId) {
        return this.storeIndustryRepository.create(industryId);
    }

    @Transactional
    @Override
    public StoreIndustry addStoreIndustry(StoreIndustry industry) {
        return this.storeIndustryRepository.save(industry);
    }

    private StoreIndustry requiredStoreIndustry(StoreIndustryId industryId) {
        return this.storeIndustryRepository.findById(industryId).orElseThrow();
    }

    @Override
    public SliceList<StoreIndustry> getStoreIndustries(StoreIndustryQuery query) {
        return this.storeIndustryRepository.findAll(query);
    }

    @Transactional
    @Override
    public StoreIndustry updateStoreIndustry(StoreIndustry source) {
        var industry = this.requiredStoreIndustry(source.toId());

        return this.storeIndustryRepository.save(industry);
    }

    @Transactional
    @Override
    public void deleteStoreIndustry(StoreIndustryId industryId) {
        var industry = this.requiredStoreIndustry(industryId);
        this.storeIndustryRepository.delete(industry);
    }
}
