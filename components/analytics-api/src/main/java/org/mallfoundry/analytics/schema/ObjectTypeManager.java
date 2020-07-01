package org.mallfoundry.analytics.schema;

import java.util.Optional;

public interface ObjectTypeManager {

    ObjectType createObjectType(String typeId);

    ObjectType addObjectType(ObjectType objectType);

    Optional<ObjectType> getObjectType(String typeId);

    void deleteObjectType(String typeId);
}
