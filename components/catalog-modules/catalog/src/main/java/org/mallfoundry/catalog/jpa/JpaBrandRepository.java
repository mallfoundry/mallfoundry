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

package org.mallfoundry.catalog.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.catalog.BrandQuery;
import org.mallfoundry.catalog.BrandRepository;
import org.mallfoundry.catalog.InternalBrand;
import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;

@Repository
public interface JpaBrandRepository
        extends BrandRepository,
        JpaRepository<InternalBrand, String>,
        JpaSpecificationExecutor<InternalBrand> {

    @Override
    default SliceList<InternalBrand> findAll(BrandQuery brandQuery) {
        Page<InternalBrand> page = this.findAll((Specification<InternalBrand>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (CollectionUtils.isNotEmpty(brandQuery.getCategories())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("categories")).value(brandQuery.getCategories()));
            }
            return predicate;
        }, PageRequest.of(brandQuery.getPage() - 1, brandQuery.getLimit()));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(brandQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
