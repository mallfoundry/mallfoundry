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

package org.mallfoundry.order.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.order.OrderQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@Repository
public interface JpaOrderRepositoryDelegate
        extends JpaRepository<JpaOrder, String>,
        JpaSpecificationExecutor<JpaOrder> {

    default Specification<JpaOrder> createSpecification(OrderQuery orderQuery) {
        return (Specification<JpaOrder>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(orderQuery.getCustomerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("customerId"), orderQuery.getCustomerId()));
            }
            if (Objects.nonNull(orderQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), orderQuery.getStoreId()));
            }
            if (CollectionUtils.isNotEmpty(orderQuery.getIds())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("id")).value(orderQuery.getIds()));
            }
            if (CollectionUtils.isNotEmpty(orderQuery.getStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("status")).value(orderQuery.getStatuses()));
            }
            if (CollectionUtils.isNotEmpty(orderQuery.getTypes())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("type")).value(orderQuery.getTypes()));
            }
            if (CollectionUtils.isNotEmpty(orderQuery.getSources())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("source")).value(orderQuery.getSources()));
            }
            if (CollectionUtils.isNotEmpty(orderQuery.getPaymentMethods())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("paymentMethod")).value(orderQuery.getPaymentMethods()));
            }
            if (Objects.nonNull(orderQuery.getPlacedTimeMin())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("placedTime"), orderQuery.getPlacedTimeMin()));
            }
            if (Objects.nonNull(orderQuery.getPlacedTimeMax())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("placedTime"), orderQuery.getPlacedTimeMax()));
            }
            if (Objects.nonNull(orderQuery.getExpiredTimeMin())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("expiredTime"), orderQuery.getExpiredTimeMin()));
            }
            if (Objects.nonNull(orderQuery.getExpiredTimeMax())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("expiredTime"), orderQuery.getExpiredTimeMax()));
            }
            return predicate;
        };
    }

    default SliceList<JpaOrder> findAll(OrderQuery orderQuery) {
        Page<JpaOrder> page = this.findAll(this.createSpecification(orderQuery),
                PageRequest.of(orderQuery.getPage() - 1, orderQuery.getLimit(), Sort.by(Sort.Order.desc("placedTime"))));
        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(orderQuery.getLimit())
                .totalSize(page.getTotalElements());
    }

    default long count(OrderQuery orderQuery) {
        return this.count(this.createSpecification(orderQuery));
    }
}
