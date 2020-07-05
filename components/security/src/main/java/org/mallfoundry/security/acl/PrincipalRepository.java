package org.mallfoundry.security.acl;

import java.util.Optional;

public interface PrincipalRepository {

    MutablePrincipal create(String id);

    Principal save(Principal principal);

    Optional<Principal> findByTypeAndName(String type, String name);
}
