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

package org.mallfoundry.follow.repository.jpa;

import org.mallfoundry.data.PageList;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.follow.FollowStoreQuery;
import org.mallfoundry.follow.FollowStoreRepository;
import org.mallfoundry.follow.InternalFollowStore;
import org.mallfoundry.follow.InternalFollowStoreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@Repository
public interface JpaFollowStoreRepository extends
        FollowStoreRepository, JpaRepository<InternalFollowStore, InternalFollowStoreId>,
        JpaSpecificationExecutor<InternalFollowStore> {

    @Override
    default SliceList<InternalFollowStore> findAll(FollowStoreQuery followQuery) {
        Page<InternalFollowStore> page = this.findAll((Specification<InternalFollowStore>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(followQuery.getFollowerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("followerId"), followQuery.getFollowerId()));
            }
            return predicate;
        }, PageRequest.of(followQuery.getPage() - 1, followQuery.getLimit()));
        return PageList.of(page.getContent()).page(page.getNumber()).limit(followQuery.getLimit()).totalSize(page.getTotalElements());
    }

    @Override
    default long count(FollowStoreQuery followQuery) {
        return this.count((Specification<InternalFollowStore>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(followQuery.getFollowerId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("followerId"), followQuery.getFollowerId()));
            }
            return predicate;
        });
    }
}
