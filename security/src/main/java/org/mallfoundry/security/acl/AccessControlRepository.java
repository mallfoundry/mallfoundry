package org.mallfoundry.security.acl;

import java.util.Optional;

public interface AccessControlRepository {

    Optional<InternalAccessControl> findByResource(Resource resource);

    <S extends InternalAccessControl> S save(S entity);

}
