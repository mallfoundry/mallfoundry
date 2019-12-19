package com.mallfoundry.catalog.jpa;

import com.mallfoundry.catalog.Category;
import com.mallfoundry.catalog.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends CategoryRepository, JpaRepository<Category, Integer> {

}
