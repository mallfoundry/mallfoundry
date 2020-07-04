package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AccessControlManager {

    Principal createPrincipal(String type, String name);

    Resource createResource(Object resource);

    Resource createResource(String type, Serializable identifier);

    Permission createPermission(String mask);

    AccessControl createAccessControl(Principal owner, Resource resource);

    Optional<AccessControl> getAccessControl(Resource resource);

    Optional<AccessControl> getAccessControl(Resource resource, List<Principal> principals);

    void grantPermission(Permission permission, Resource resource, Principal principal);

    void grantPermissions(List<Permission> permissions, Resource resource, Principal principal);

    void revokePermission(Permission permission, Resource resource, Principal principal);

    void revokePermissions(List<Permission> permissions, Resource resource, Principal principal);
}
