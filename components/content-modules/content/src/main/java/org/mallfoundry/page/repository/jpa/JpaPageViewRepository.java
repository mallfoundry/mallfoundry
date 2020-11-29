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

package org.mallfoundry.page.repository.jpa;

import org.mallfoundry.data.repository.jpa.SortUtils;
import org.mallfoundry.page.PageViewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaPageViewRepository extends JpaRepository<JpaPageView, String>, JpaSpecificationExecutor<JpaPageView> {

    default Specification<JpaPageView> createSpecification(PageViewQuery viewQuery) {
        return (Specification<JpaPageView>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(viewQuery.getPageId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("pageId"), viewQuery.getPageId()));
            }
            if (Objects.nonNull(viewQuery.getBrowserId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("browserId"), viewQuery.getBrowserId()));
            }
            if (Objects.nonNull(viewQuery.getBrowserIp())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("browserIp"), viewQuery.getBrowserIp()));
            }
            if (Objects.nonNull(viewQuery.getBrowsingTimeFrom())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("browsingTime"), viewQuery.getBrowsingTimeFrom()));
            }
            if (Objects.nonNull(viewQuery.getBrowsingTimeTo())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("browsingTime"), viewQuery.getBrowsingTimeTo()));
            }
            return predicate;
        };
    }

    default Page<JpaPageView> findAll(PageViewQuery viewQuery) {
        return this.findAll(this.createSpecification(viewQuery),
                PageRequest.of(viewQuery.getPage() - 1, viewQuery.getLimit(), SortUtils.createSort(viewQuery)));
    }

    default long count(PageViewQuery query) {
        return this.count(this.createSpecification(query));
    }
}
