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

package org.mallfoundry.member.repository.jpa;

import org.mallfoundry.member.MemberQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

public interface JpaMemberRepository extends JpaRepository<JpaMember, JpaMemberId>, JpaSpecificationExecutor<JpaMember> {

    default Specification<JpaMember> createSpecification(MemberQuery memberQuery) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.nonNull(memberQuery.getStoreId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("storeId"), memberQuery.getStoreId()));
            }
            return predicate;
        };
    }

    default Page<JpaMember> findAll(MemberQuery query) {
        return this.findAll(this.createSpecification(query),
                PageRequest.of(query.getPage() - 1, query.getLimit(), Sort.by("createdTime").ascending()));
    }

    default long count(MemberQuery query) {
        return this.count(this.createSpecification(query));
    }

    void deleteAllByTenantIdAndStoreId(String tenantId, String storeId);
}
