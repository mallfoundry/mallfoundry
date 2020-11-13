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
import org.mallfoundry.finance.WithdrawalQuery;
import org.mallfoundry.util.CaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaWithdrawalRepository extends JpaRepository<JpaWithdrawal, String>, JpaSpecificationExecutor<JpaWithdrawal> {

    default Specification<JpaWithdrawal> createSpecification(WithdrawalQuery withdrawalQuery) {
        return (Specification<JpaWithdrawal>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(withdrawalQuery.getAccountId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), withdrawalQuery.getAccountId()));
            }

            if (CollectionUtils.isNotEmpty(withdrawalQuery.getStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("status")).value(withdrawalQuery.getStatuses()));
            }

            if (Objects.nonNull(withdrawalQuery.getAppliedTimeStart())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("appliedTime"), withdrawalQuery.getAppliedTimeStart()));
            }

            if (Objects.nonNull(withdrawalQuery.getAppliedTimeEnd())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("appliedTime"), withdrawalQuery.getAppliedTimeEnd()));
            }
            return predicate;
        };
    }

    private Sort createSort(WithdrawalQuery query) {
        return Optional.ofNullable(query.getSort())
                .map(org.mallfoundry.data.Sort::getOrders)
                .filter(CollectionUtils::isNotEmpty)
                .map(orders -> Sort.by(orders.stream()
                        .peek(sortOrder -> sortOrder.setProperty(CaseUtils.camelCase(sortOrder.getProperty())))
                        .map(sortOrder -> sortOrder.getDirection().isDescending()
                                ? Sort.Order.desc(sortOrder.getProperty())
                                : Sort.Order.asc(sortOrder.getProperty()))
                        .collect(Collectors.toUnmodifiableList())))
                .orElseGet(() -> Sort.by("appliedTime").descending());
    }

    default Page<JpaWithdrawal> findAll(WithdrawalQuery query) {
        var sort = this.createSort(query);
        return this.findAll(this.createSpecification(query), PageRequest.of(query.getPage() - 1, query.getLimit(), sort));
    }
}
