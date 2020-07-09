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

package org.mallfoundry.district.repository.jpa;

import org.mallfoundry.district.CountyRepository;
import org.mallfoundry.district.DistrictQuery;
import org.mallfoundry.district.InternalCounty;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaCountyRepository
        extends CountyRepository, JpaRepository<InternalCounty, String>,
        JpaSpecificationExecutor<InternalCounty> {

    default Specification<InternalCounty> createSpecification(DistrictQuery districtQuery) {
        return (Specification<InternalCounty>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (Objects.nonNull(districtQuery.getCityId())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("cityId"), districtQuery.getCityId()));
            }

            if (Objects.nonNull(districtQuery.getCode())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("code"), districtQuery.getCode()));
            }
            return predicate;
        };
    }

    @Override
    default List<InternalCounty> findAll(DistrictQuery query) {
        return this.findAll(this.createSpecification(query));
    }
}
