package org.mallfoundry.catalog;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    <S extends InternalCategory> S save(S category);

    Optional<InternalCategory> findById(Integer id);

    <S extends InternalCategory> List<S> findAll(Example<S> example, Sort sort);
}
