package org.mallfoundry.analytics.schema;

import java.util.Optional;

public interface ObjectTypeRepository {

    ObjectType create(String id);

    ObjectType save(ObjectType objectType);

    Optional<ObjectType> findById(String id);

    void delete(ObjectType objectType);
}
