package org.mallfoundry.catalog;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    <S extends InternalCategory> S save(S category);

    Optional<InternalCategory> findById(String id);

    List<InternalCategory> findAll(CategoryQuery query);

    void delete(InternalCategory category);
}
