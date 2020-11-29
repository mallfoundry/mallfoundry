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

package org.mallfoundry.customer.repository.jpa;

import org.mallfoundry.customer.SearchTerm;
import org.mallfoundry.customer.SearchTermRepository;
import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.Optional;

public class DelegatingJpaSearchTermRepository implements SearchTermRepository {

    private final JpaSearchTermRepository repository;

    public DelegatingJpaSearchTermRepository(JpaSearchTermRepository repository) {
        this.repository = repository;
    }

    @Override
    public SearchTerm create(String customerId, String term) {
        return new JpaSearchTerm(customerId, term);
    }

    @Override
    public Optional<SearchTerm> findByCustomerIdAndTerm(String customerId, String term) {
        return CastUtils.cast(this.repository.findById(new JpaSearchTermId(customerId, term)));
    }

    @Override
    public List<SearchTerm> findAllByCustomerId(String customerId) {
        return CastUtils.cast(this.repository.findAllByCustomerId(customerId));
    }

    @Override
    public SearchTerm save(SearchTerm term) {
        return this.repository.save(JpaSearchTerm.of(term));
    }

    @Override
    public void deleteByCustomerIdAndTerm(String customerId, String term) {
        this.repository.deleteByCustomerIdAndTerm(customerId, term);
    }

    @Override
    public void deleteByCustomerIdAndTerms(String customerId, List<String> terms) {
        this.repository.deleteByCustomerIdAndTermIn(customerId, terms);
    }

    @Override
    public void deleteByCustomerId(String customerId) {
        this.repository.deleteByCustomerId(customerId);
    }
}
