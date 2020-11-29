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

package org.mallfoundry.marketing.coupon.repository.jpa;

import org.mallfoundry.marketing.coupon.CouponQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaTakeCouponRepository extends JpaRepository<JpaTakeCoupon, String>, JpaSpecificationExecutor<JpaTakeCoupon> {

    default Specification<JpaTakeCoupon> createSpecification(CouponQuery couponQuery) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(couponQuery.getTenantId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("tenantId"), couponQuery.getTenantId()));
            }
            if (Objects.nonNull(couponQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), couponQuery.getStoreId()));
            }
            if (Objects.nonNull(couponQuery.getCustomerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("customerId"), couponQuery.getCustomerId()));
            }
            return predicate;
        };
    }

    default Page<JpaTakeCoupon> findAll(CouponQuery query) {
        return this.findAll(this.createSpecification(query), PageRequest.of(query.getPage() - 1, query.getLimit()));
    }

    default long count(CouponQuery query) {
        return this.count(this.createSpecification(query));
    }
}
