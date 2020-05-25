package org.mallfoundry.catalog.jpa;

import org.mallfoundry.catalog.Category;
import org.mallfoundry.catalog.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends CategoryRepository, JpaRepository<Category, Integer> {

}
