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

package org.mallfoundry.browsing.repository.jpa;


import org.mallfoundry.browsing.BrowsingProductQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface JpaBrowsingProductRepository
        extends JpaRepository<JpaBrowsingProduct, JpaBrowsingProductId>,
        JpaSpecificationExecutor<JpaBrowsingProduct> {

    @Override
    <S extends JpaBrowsingProduct> S save(S entity);

    Optional<JpaBrowsingProduct> findByIdAndBrowserId(String id, String browserId);

    List<JpaBrowsingProduct> findAllByIdInAndBrowserId(Iterable<String> ids, String browserId);

    default Specification<JpaBrowsingProduct> createSpecification(BrowsingProductQuery bpQuery) {
        return (Specification<JpaBrowsingProduct>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(bpQuery.getBrowserId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("browserId"), bpQuery.getBrowserId()));
            }
            return predicate;
        };
    }

    default Page<JpaBrowsingProduct> findAll(BrowsingProductQuery query) {
        return this.findAll(this.createSpecification(query),
                PageRequest.of(query.getPage() - 1, query.getLimit(), Sort.by(Sort.Order.desc("browsingTime"))));
    }

    default long count(BrowsingProductQuery query) {
        return this.count(this.createSpecification(query));
    }

    void deleteAllByIdIn(Collection<JpaBrowsingProductId> ids);
}
