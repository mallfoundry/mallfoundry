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

import org.mallfoundry.catalog.product.review.ProductReviewQuery;
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
public interface JpaProductReviewRepositoryDelegate
        extends JpaRepository<JpaProductReview, String>, JpaSpecificationExecutor<JpaProductReview> {

    default Specification<JpaProductReview> createSpecification(ProductReviewQuery reviewQuery) {
        return (Specification<JpaProductReview>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(reviewQuery.getProductId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("productId"), reviewQuery.getProductId()));
            }
            if (Objects.nonNull(reviewQuery.getVariantId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("variantId"), reviewQuery.getVariantId()));
            }
            return predicate;
        };
    }

    default Page<JpaProductReview> findAll(ProductReviewQuery reviewQuery) {
        var sort = Objects.nonNull(reviewQuery.getSort())
                ? Sort.by(
                reviewQuery.getSort().getOrders().stream()
                        .map(aOrder -> aOrder.isAscending()
                                ? Sort.Order.asc(aOrder.getProperty())
                                : Sort.Order.desc(aOrder.getProperty()))
                        .toArray(Sort.Order[]::new))
                : Sort.by(Sort.Order.desc("reviewedTime"));

        return this.findAll(this.createSpecification(reviewQuery),
                PageRequest.of(reviewQuery.getPage() - 1, reviewQuery.getLimit(), sort));
    }
}
