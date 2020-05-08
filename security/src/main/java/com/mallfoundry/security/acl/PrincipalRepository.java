package com.mallfoundry.security.acl;

import java.util.Optional;

public interface PrincipalRepository {

    Optional<InternalPrincipal> findByTypeAndName(String type, String name);
}
