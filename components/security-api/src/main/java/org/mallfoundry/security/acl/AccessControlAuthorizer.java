package org.mallfoundry.security.acl;

import java.util.Set;

public interface AccessControlAuthorizer {

    boolean hasPermission(Principal principal, Resource resource, Permission permission);

    boolean hasPermission(Set<Principal> principals, Resource resource, Permission permission);

    boolean hasAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions);

    boolean hasAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions);

    void checkPermission(Principal principal, Resource resource, Permission permission) throws AccessDeniedException;

    void checkPermission(Set<Principal> principals, Resource resource, Permission permission) throws AccessDeniedException;

    void checkAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) throws AccessDeniedException;

    void checkAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) throws AccessDeniedException;
}
