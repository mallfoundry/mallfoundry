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

package org.mallfoundry.finance.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.finance.RechargeQuery;
import org.mallfoundry.util.CaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface JpaRechargeRepository extends JpaRepository<JpaRecharge, String>, JpaSpecificationExecutor<JpaRecharge> {

    default Specification<JpaRecharge> createSpecification(RechargeQuery rechargeQuery) {
        return (Specification<JpaRecharge>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(rechargeQuery.getAccountId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), rechargeQuery.getAccountId()));
            }

            if (CollectionUtils.isNotEmpty(rechargeQuery.getStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("status")).value(rechargeQuery.getStatuses()));
            }

            if (Objects.nonNull(rechargeQuery.getCreatedTimeStart())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), rechargeQuery.getCreatedTimeStart()));
            }

            if (Objects.nonNull(rechargeQuery.getCreatedTimeEnd())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("createdTime"), rechargeQuery.getCreatedTimeEnd()));
            }
            return predicate;
        };
    }

    private Sort createSort(RechargeQuery query) {
        return Optional.ofNullable(query.getSort())
                .map(org.mallfoundry.data.Sort::getOrders)
                .filter(CollectionUtils::isNotEmpty)
                .map(orders -> Sort.by(orders.stream()
                        .peek(sortOrder -> sortOrder.setProperty(CaseUtils.camelCase(sortOrder.getProperty())))
                        .map(sortOrder -> sortOrder.getDirection().isDescending()
                                ? Sort.Order.desc(sortOrder.getProperty())
                                : Sort.Order.asc(sortOrder.getProperty()))
                        .collect(Collectors.toUnmodifiableList())))
                .orElseGet(() -> Sort.by("createdTime").descending());
    }

    default Page<JpaRecharge> findAll(RechargeQuery query) {
        var sort = this.createSort(query);
        return this.findAll(this.createSpecification(query), PageRequest.of(query.getPage() - 1, query.getLimit(), sort));
    }
}
