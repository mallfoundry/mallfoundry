package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

public interface AccessControlManager {

    Principal createPrincipal(String type, String name);

    Principal addPrincipal(Principal principal);

    Principal addPrincipal(String type, String name);

    Optional<Principal> getPrincipal(String type, String name);

    Resource createResource(Object resource);

    Resource createResource(String type, Serializable identifier);

    Optional<Resource> getResource(Object resource);

    Optional<Resource> getResource(String type, Serializable identifier);

    Permission createPermission(String mask);

    AccessControl createAccessControl(Principal owner, Resource resource);

    AccessControl addAccessControl(AccessControl accessControl);

    Optional<AccessControl> getAccessControl(Resource resource);

    Optional<AccessControl> getAccessControl(Resource resource, Set<Principal> principals);

    void grantPermission(Permission permission, Resource resource, Principal principal);

    void grantPermissions(Set<Permission> permissions, Resource resource, Principal principal);

    void revokePermission(Permission permission, Resource resource, Principal principal);

    void revokePermissions(Set<Permission> permissions, Resource resource, Principal principal);
}
