package org.mallfoundry.catalog;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    <S extends Category> S save(S category);

    Optional<Category> findById(Integer id);

    <S extends Category> List<S> findAll(Example<S> example, Sort sort);
}
