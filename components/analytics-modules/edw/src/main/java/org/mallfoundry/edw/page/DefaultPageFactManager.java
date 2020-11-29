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

package org.mallfoundry.edw.page;

import org.springframework.transaction.annotation.Transactional;

public class DefaultPageFactManager implements PageFactManager {

    private final PageFactRepository pageFactRepository;

    public DefaultPageFactManager(PageFactRepository pageFactRepository) {
        this.pageFactRepository = pageFactRepository;
    }

    @Override
    public PageFactKey createPageFactKey() {
        return new ImmutablePageFactKey();
    }

    @Override
    public PageFact createPageFact(PageFactKey factKey) {
        return this.pageFactRepository.create(factKey);
    }

    private PageFact getPageFact(PageFactKey key) {
        return this.pageFactRepository.findByKey(key)
                .orElseGet(() -> this.pageFactRepository.create(key));
    }

    @Transactional
    @Override
    public PageFact savePageFact(PageFact source) {
        var fact = this.getPageFact(source.toKey());
        fact.adjustViewCount(source.getViewCount());
        return this.pageFactRepository.save(fact);
    }
}
