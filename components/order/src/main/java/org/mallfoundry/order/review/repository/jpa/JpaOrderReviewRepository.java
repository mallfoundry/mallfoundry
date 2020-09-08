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

package org.mallfoundry.order.review.repository.jpa;

import org.mallfoundry.order.aftersales.repository.jpa.JpaOrderDispute;
import org.mallfoundry.order.repository.jpa.JpaOrderReview;
import org.mallfoundry.order.review.OrderReviewQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaOrderReviewRepository extends JpaRepository<JpaOrderReview, String>, JpaSpecificationExecutor<JpaOrderReview> {

    default Specification<JpaOrderReview> createSpecification(OrderReviewQuery reviewQuery) {
        return (Specification<JpaOrderReview>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(reviewQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), reviewQuery.getStoreId()));
            }
            if (Objects.nonNull(reviewQuery.getCustomerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("customerId"), reviewQuery.getCustomerId()));
            }
            if (Objects.nonNull(reviewQuery.getOrderId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("orderId"), reviewQuery.getOrderId()));
            }
            return predicate;
        };
    }

    default Page<JpaOrderReview> findAll(OrderReviewQuery query) {
        return this.findAll(this.createSpecification(query), PageRequest.of(query.getPage() - 1, query.getLimit()));
    }

}
