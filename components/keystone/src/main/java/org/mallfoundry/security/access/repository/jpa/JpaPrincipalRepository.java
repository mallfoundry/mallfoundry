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

package org.mallfoundry.security.access.repository.jpa;

import org.mallfoundry.security.access.Principal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public interface JpaPrincipalRepository extends JpaRepository<JpaPrincipal, String>, JpaSpecificationExecutor<JpaPrincipal> {
    Optional<Principal> findByTypeAndName(String type, String name);


    default Specification<JpaPrincipal> createSpecification(List<Principal> principals) {
        return (Specification<JpaPrincipal>) (root, query, criteriaBuilder) -> {
            var predicates = principals.stream()
                    .map(principal -> criteriaBuilder.and(criteriaBuilder.equal(root.get("type"),
                            principal.getType()), criteriaBuilder.equal(root.get("name"), principal.getName())))
                    .toArray(Predicate[]::new);
            return criteriaBuilder.or(predicates);
        };
    }

    default List<JpaPrincipal> findAll(List<Principal> principals) {
        return this.findAll(this.createSpecification(principals));
    }
}
