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

package org.mallfoundry.store.staff.repository.jpa;

import org.apache.commons.collections4.CollectionUtils;
import org.mallfoundry.store.staff.StaffId;
import org.mallfoundry.store.staff.StaffStoreQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;

public interface JpaOnlyStaffRepository extends JpaRepository<JpaOnlyStaff, StaffId>, JpaSpecificationExecutor<JpaOnlyStaff> {

    default Specification<JpaOnlyStaff> createSpecification(StaffStoreQuery staffQuery) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (CollectionUtils.isNotEmpty(staffQuery.getStaffIds())) {
                predicate.getExpressions().add(criteriaBuilder.in(root.get("id")).value(staffQuery.getStaffIds()));
            }
            return predicate;
        };
    }

    default Page<JpaOnlyStaff> findAll(StaffStoreQuery query) {
        return this.findAll(this.createSpecification(query),
                PageRequest.of(query.getPage() - 1, query.getLimit(), Sort.by("createdTime").ascending()));
    }
}
