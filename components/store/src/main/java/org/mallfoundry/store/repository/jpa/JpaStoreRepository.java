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

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.store.InternalStore;
import org.mallfoundry.store.StoreQuery;
import org.mallfoundry.store.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@Repository
public interface JpaStoreRepository
        extends StoreRepository,
        JpaRepository<InternalStore, String>,
        JpaSpecificationExecutor<InternalStore> {

    @Override
    default SliceList<InternalStore> findAll(StoreQuery storeQuery) {
        Page<InternalStore> page = this.findAll((Specification<InternalStore>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(storeQuery.getOwnerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("ownerId"), storeQuery.getOwnerId()));
            }

            return predicate;
        }, PageRequest.of(storeQuery.getPage() - 1, storeQuery.getLimit()));

        return PageList.of(page.getContent())
                .page(page.getNumber())
                .limit(storeQuery.getLimit())
                .totalSize(page.getTotalElements());
    }
}
