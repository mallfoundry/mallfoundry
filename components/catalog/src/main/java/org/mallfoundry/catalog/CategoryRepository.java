package org.mallfoundry.catalog;

import java.util.Optional;

public interface CategoryRepository {

    <S extends InternalCategory> S save(S category);

    Optional<InternalCategory> findById(String id);

    void delete(InternalCategory category);
}
