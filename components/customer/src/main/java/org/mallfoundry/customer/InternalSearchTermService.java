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

package org.mallfoundry.customer;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
public class InternalSearchTermService implements SearchTermService {

    private final SearchTermRepository searchTermRepository;

    public InternalSearchTermService(SearchTermRepository searchTermRepository) {
        this.searchTermRepository = searchTermRepository;
    }

    private Supplier<InternalSearchTerm> createNewSearchTerm(String customerId, String term) {
        return () -> new InternalSearchTerm(customerId, term);
    }

    @Transactional
    @Override
    public SearchTerm addSearchTerm(String customerId, String termText) {
        var term = this.searchTermRepository
                .findById(InternalSearchTermId.of(customerId, termText))
                .orElseGet(createNewSearchTerm(customerId, termText));
        term.hit();
        return this.searchTermRepository.save(term);
    }

    @Override
    public List<SearchTerm> getSearchTerms(String customerId) {
        return CastUtils.cast(this.searchTermRepository.findAllByCustomerId(customerId));
    }

    @Transactional
    @Override
    public void deleteSearchTerm(String customerId, String term) {
        this.searchTermRepository.deleteByCustomerIdAndTerm(customerId, term);
    }

    @Transactional
    @Override
    public void deleteSearchTerms(String customerId, List<String> terms) {
        this.searchTermRepository.deleteByCustomerIdAndTermIn(customerId, terms);
    }

    @Transactional
    @Override
    public void clearSearchTerms(String customerId) {
        this.searchTermRepository.deleteByCustomerId(customerId);
    }
}
