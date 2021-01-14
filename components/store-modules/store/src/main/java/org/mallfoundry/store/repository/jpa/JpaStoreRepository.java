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

package org.mallfoundry.store.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.store.StoreQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaStoreRepository
        extends JpaRepository<JpaStore, String>, JpaSpecificationExecutor<JpaStore> {

    default Page<JpaStore> findAll(StoreQuery storeQuery) {
        return this.findAll((Specification<JpaStore>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(storeQuery.getOwnerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("ownerId"), storeQuery.getOwnerId()));
            }

            if (CollectionUtils.isNotEmpty(storeQuery.getStaffIds())) {
                predicate.getExpressions().add(root.joinSet("staffIds", JoinType.LEFT).as(String.class).in(storeQuery.getStaffIds()));
            }

            return predicate;
        }, PageRequest.of(storeQuery.getPage() - 1, storeQuery.getLimit(), Sort.by("createdTime").ascending()));
    }
}
