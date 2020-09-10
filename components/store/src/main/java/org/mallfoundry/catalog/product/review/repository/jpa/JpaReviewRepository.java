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

package org.mallfoundry.catalog.product.review.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.product.review.ReviewQuery;
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
public interface JpaReviewRepository
        extends JpaRepository<JpaReview, String>, JpaSpecificationExecutor<JpaReview> {

    default Specification<JpaReview> createSpecification(ReviewQuery reviewQuery) {
        return (Specification<JpaReview>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(reviewQuery.getProductId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("productId"), reviewQuery.getProductId()));
            }
            if (Objects.nonNull(reviewQuery.getVariantId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("variantId"), reviewQuery.getVariantId()));
            }
            if (CollectionUtils.isNotEmpty(reviewQuery.getRatings())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("rating")).value(reviewQuery.getRatings()));
            }
            return predicate;
        };
    }

    private Sort createSort(ReviewQuery query) {
        return Optional.ofNullable(query.getSort())
                .map(org.mallfoundry.data.Sort::getOrders)
                .filter(CollectionUtils::isNotEmpty)
                .map(orders -> Sort.by(orders.stream()
                        .peek(sortOrder -> sortOrder.setProperty(CaseUtils.camelCase(sortOrder.getProperty())))
                        .map(sortOrder -> sortOrder.getDirection().isDescending()
                                ? Sort.Order.desc(sortOrder.getProperty())
                                : Sort.Order.asc(sortOrder.getProperty()))
                        .collect(Collectors.toUnmodifiableList())))
                .orElseGet(Sort::unsorted);
    }

    default Page<JpaReview> findAll(ReviewQuery reviewQuery) {
        return this.findAll(this.createSpecification(reviewQuery),
                PageRequest.of(reviewQuery.getPage() - 1, reviewQuery.getLimit(), this.createSort(reviewQuery)));
    }

    default long count(ReviewQuery reviewQuery) {
        return this.count(this.createSpecification(reviewQuery));
    }
}
