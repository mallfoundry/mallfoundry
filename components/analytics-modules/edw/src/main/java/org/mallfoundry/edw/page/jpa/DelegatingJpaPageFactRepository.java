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

package org.mallfoundry.edw.page.jpa;

import org.mallfoundry.edw.page.PageFact;
import org.mallfoundry.edw.page.PageFactKey;
import org.mallfoundry.edw.page.PageFactRepository;
import org.springframework.data.util.CastUtils;

import java.util.Optional;

public class DelegatingJpaPageFactRepository implements PageFactRepository {

    private final JpaPageFactRepository repository;

    public DelegatingJpaPageFactRepository(JpaPageFactRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageFact create(PageFactKey factKey) {
        return new JpaPageFact(factKey);
    }

    @Override
    public Optional<PageFact> findByKey(PageFactKey factKey) {
        return CastUtils.cast(this.repository.findById(CastUtils.cast(factKey)));
    }

    @Override
    public PageFact save(PageFact fact) {
        return this.repository.save(CastUtils.cast(fact));
    }
}
