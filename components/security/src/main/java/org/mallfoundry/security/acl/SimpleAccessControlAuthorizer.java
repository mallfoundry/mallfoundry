package org.mallfoundry.security.acl;

import org.springframework.stereotype.Service;

import java.util.Set;

import static org.mallfoundry.i18n.MessageHolder.message;

@Service
public class SimpleAccessControlAuthorizer implements AccessControlAuthorizer {

    private final AccessControlManager manager;

    public SimpleAccessControlAuthorizer(AccessControlManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean hasPermission(Principal principal, Resource resource, Permission permission) {
        return this.hasPermission(Set.of(principal), resource, permission);
    }

    @Override
    public boolean hasPermission(Set<Principal> principals, Resource resource, Permission permission) {
        return this.hasAnyPermissions(principals, resource, Set.of(permission));
    }

    @Override
    public boolean hasAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) {
        return this.hasAnyPermissions(Set.of(principal), resource, permissions);
    }

    @Override
    public boolean hasAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) {
        return this.manager.getAccessControl(resource, principals)
                .orElseThrow()
                .granted(principals, permissions);
    }

    @Override
    public void checkPermission(Principal principal, Resource resource, Permission permission) throws AccessDeniedException {
        if (!this.hasPermission(principal, resource, permission)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkPermission(Set<Principal> principals, Resource resource, Permission permission) throws AccessDeniedException {
        if (!this.hasPermission(principals, resource, permission)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) throws AccessDeniedException {
        if (!this.hasAnyPermissions(principal, resource, permissions)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) throws AccessDeniedException {
        if (!this.hasAnyPermissions(principals, resource, permissions)) {
            this.denyAccess();
        }
    }

    private void denyAccess() {
        throw new AccessDeniedException(message("security.accessDenied", "Access is denied"));
    }
}
