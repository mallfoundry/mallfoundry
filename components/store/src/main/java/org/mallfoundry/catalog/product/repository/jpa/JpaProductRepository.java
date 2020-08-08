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

package org.mallfoundry.catalog.product.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.catalog.product.ProductQuery;
import org.mallfoundry.util.CaseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface JpaProductRepository extends JpaRepository<JpaProduct, String>, JpaSpecificationExecutor<JpaProduct> {

    private Sort createSort(ProductQuery query) {
        return Optional.ofNullable(query.getSort())
                .map(aSort -> Sort.by(aSort.getOrders().stream()
                        .peek(sortOrder -> sortOrder.setProperty(CaseUtils.camelCase(sortOrder.getProperty())))
                        .map(sortOrder -> sortOrder.getDirection().isDescending()
                                ? Sort.Order.desc(sortOrder.getProperty())
                                : Sort.Order.asc(sortOrder.getProperty()))
                        .collect(Collectors.toUnmodifiableList())))
                .orElseGet(Sort::unsorted);
    }

    default Specification<JpaProduct> createSpecification(ProductQuery productQuery) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotEmpty(productQuery.getName())) {
                predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + productQuery.getName() + "%"));
            }

            if (Objects.nonNull(productQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), productQuery.getStoreId()));
            }

            if (Objects.nonNull(productQuery.getPriceMin())) {
                predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productQuery.getPriceMin()));
            }

            if (Objects.nonNull(productQuery.getPriceMax())) {
                predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), productQuery.getPriceMax()));
            }

            if (CollectionUtils.isNotEmpty(productQuery.getStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("status")).value(productQuery.getStatuses()));
            }

            if (CollectionUtils.isNotEmpty(productQuery.getInventoryStatuses())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("inventoryStatus")).value(productQuery.getInventoryStatuses()));
            }

            if (CollectionUtils.isNotEmpty(productQuery.getCollections())) {
                predicate.getExpressions().add(root.joinSet("collections", JoinType.LEFT).as(String.class).in(productQuery.getCollections()));
            }

            return predicate;
        };
    }

    default Page<JpaProduct> findAll(ProductQuery query) {
        return this.findAll(this.createSpecification(query),
                PageRequest.of(query.getPage() - 1, query.getLimit(), this.createSort(query)));
    }
}
