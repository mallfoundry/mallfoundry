package org.mallfoundry.catalog.jpa;

import org.mallfoundry.catalog.CategoryQuery;
import org.mallfoundry.catalog.CategoryRepository;
import org.mallfoundry.catalog.InternalCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;

@Repository
public interface JpaCategoryRepository
        extends CategoryRepository,
        JpaRepository<InternalCategory, String>,
        JpaSpecificationExecutor<InternalCategory> {

    @Override
    default List<InternalCategory> findAll(CategoryQuery categoryQuery) {
        return this.findAll((Specification<InternalCategory>) (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (Objects.isNull(categoryQuery.getParentId())) {
                predicate.getExpressions().add(criteriaBuilder.isNull(root.get("parentId")));
            } else {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("parentId"), categoryQuery.getParentId()));
            }
            return predicate;
        });
    }
}
