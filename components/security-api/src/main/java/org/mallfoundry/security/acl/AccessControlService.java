package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AccessControlService {

    Principal createPrincipal(String type, String name);

    Resource createResource(Object resource);

    Resource createResource(String type, Serializable identifier);

    Permission createPermission(String mask);

    AccessControl createAccessControl(Principal owner, Resource resource);

    Optional<AccessControl> getAccessControl(Resource resource);

    Optional<AccessControl> getAccessControl(Resource resource, List<Principal> principals);

    AccessControl saveAccessControl(AccessControl control);

}