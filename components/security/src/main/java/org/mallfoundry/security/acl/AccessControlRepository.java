package org.mallfoundry.security.acl;

import java.util.Optional;
import java.util.Set;

public interface AccessControlRepository {

    AccessControl create(String id);

    Optional<AccessControl> findByResource(Resource resource);

    Optional<AccessControl> findByResourceAndPrincipals(Resource resource, Set<Principal> principals);

    AccessControl save(AccessControl entity);
}
