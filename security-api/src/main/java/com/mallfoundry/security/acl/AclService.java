package com.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.Optional;

public interface AclService {

    Principal createPrincipal(String type, String name);

    Resource createResource(Object resource);

    Resource createResource(String type, Serializable identifier);

    Permission createPermission(String mask);

    Acl createAcl(Resource resource);

    Optional<Acl> getAcl(Resource resource);

    Acl saveAcl(Acl acl);

}
