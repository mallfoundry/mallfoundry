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
