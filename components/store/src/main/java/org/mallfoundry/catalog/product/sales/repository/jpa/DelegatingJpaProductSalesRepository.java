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

package org.mallfoundry.catalog.product.sales.repository.jpa;

import org.mallfoundry.catalog.product.sales.ProductSales;
import org.mallfoundry.catalog.product.sales.ProductSalesId;
import org.mallfoundry.catalog.product.sales.ProductSalesQuery;
import org.mallfoundry.catalog.product.sales.ProductSalesRepository;
import org.springframework.data.util.CastUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.Optional;

public class DelegatingJpaProductSalesRepository implements ProductSalesRepository {

    private final EntityManager entityManager;

    private final JpaProductSalesRepository repository;


    public DelegatingJpaProductSalesRepository(EntityManager entityManager, JpaProductSalesRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    @Override
    public ProductSales create() {
        return new JpaProductSales();
    }

    @Override
    public ProductSales save(ProductSales sales) {
        return this.repository.save(JpaProductSales.of(sales));
    }

    @Override
    public Optional<ProductSales> findById(ProductSalesId salesId) {
        return CastUtils.cast(this.repository.findById(JpaProductSalesId.of(salesId)));
    }

    @Override
    public long sumQuantities(ProductSalesQuery salesQuery) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(JpaProductSales.class);
        var root = query.from(JpaProductSales.class);
        query.multiselect(
                criteriaBuilder.sumAsLong(root.get("quantities")).alias("quantities")
        );
        Predicate predicate = criteriaBuilder.conjunction();
        if (Objects.nonNull(salesQuery.getProductId())) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("productId"), salesQuery.getProductId()));
        }
        if (Objects.nonNull(salesQuery.getVariantId())) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("variantId"), salesQuery.getVariantId()));
        }
        if (Objects.nonNull(salesQuery.getYearStart())) {
            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), salesQuery.getYearStart()));
        }
        if (Objects.nonNull(salesQuery.getYearEnd())) {
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("year"), salesQuery.getYearEnd()));
        }
        if (Objects.nonNull(salesQuery.getMonthStart())) {
            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("month"), salesQuery.getMonthStart()));
        }
        if (Objects.nonNull(salesQuery.getMonthEnd())) {
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("month"), salesQuery.getMonthEnd()));
        }
        if (Objects.nonNull(salesQuery.getDayOfMonthStart())) {
            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("dayOfMonth"), salesQuery.getDayOfMonthStart()));
        }
        if (Objects.nonNull(salesQuery.getDayOfMonthEnd())) {
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("dayOfMonth"), salesQuery.getDayOfMonthEnd()));
        }
        return this.entityManager
                .createQuery(query.where(predicate))
                .getResultStream()
                .mapToLong(JpaProductSales::getQuantities)
                .sum();
    }
}
