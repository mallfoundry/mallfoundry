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

import org.mallfoundry.catalog.product.sales.ProductSale;
import org.mallfoundry.catalog.product.sales.ProductSaleId;
import org.mallfoundry.catalog.product.sales.ProductSaleQuery;
import org.mallfoundry.catalog.product.sales.ProductSaleRepository;
import org.springframework.data.util.CastUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.Objects;
import java.util.Optional;

public class DelegatingJpaProductSaleRepository implements ProductSaleRepository {

    private final EntityManager entityManager;

    private final JpaProductSaleRepository repository;

    public DelegatingJpaProductSaleRepository(EntityManager entityManager, JpaProductSaleRepository repository) {
        this.entityManager = entityManager;
        this.repository = repository;
    }

    @Override
    public ProductSale create() {
        return new JpaProductSale();
    }

    @Override
    public ProductSale save(ProductSale sale) {
        return this.repository.save(JpaProductSale.of(sale));
    }

    @Override
    public Optional<ProductSale> findById(ProductSaleId salesId) {
        return CastUtils.cast(this.repository.findById(JpaProductSaleId.of(salesId)));
    }

    @Override
    public long sumQuantities(ProductSaleQuery saleQuery) {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(JpaProductSale.class);
        var root = query.from(JpaProductSale.class);
        query.multiselect(
                criteriaBuilder.sumAsLong(root.get("totalQuantities")).alias("totalQuantities")
        );
        Predicate predicate = criteriaBuilder.conjunction();
        if (Objects.nonNull(saleQuery.getProductId())) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("productId"), saleQuery.getProductId()));
        }
        if (Objects.nonNull(saleQuery.getVariantId())) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("variantId"), saleQuery.getVariantId()));
        }
        if (Objects.nonNull(saleQuery.getSoldDateStart())) {
            predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("soldDate"), saleQuery.getSoldDateStart()));
        }
        if (Objects.nonNull(saleQuery.getSoldDateEnd())) {
            predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("soldDate"), saleQuery.getSoldDateEnd()));
        }
        return this.entityManager
                .createQuery(query.where(predicate))
                .getResultStream()
                .mapToLong(JpaProductSale::getTotalQuantities)
                .sum();
    }
}
