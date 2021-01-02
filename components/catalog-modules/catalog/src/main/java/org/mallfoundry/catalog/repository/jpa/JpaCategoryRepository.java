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

package org.mallfoundry.catalog.repository.jpa;

import org.mallfoundry.catalog.CategoryQuery;
import org.mallfoundry.catalog.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaCategoryRepository extends
        JpaRepository<JpaCategory, String>,
        JpaSpecificationExecutor<JpaCategory> {

    default List<JpaCategory> findAll(CategoryQuery categoryQuery) {
        return this.findAll((Specification<JpaCategory>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.isNull(categoryQuery.getParentId())) {
                predicate.getExpressions().add(criteriaBuilder.isNull(root.get("parentId")));
            } else {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("parentId"), categoryQuery.getParentId()));
            }
            return predicate;
        }, Sort.by(Sort.Order.asc("position")));
    }
}
