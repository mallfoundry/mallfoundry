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
