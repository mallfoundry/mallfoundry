package org.mallfoundry.security.acl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.Set;

public abstract class AccessControlEntrySupport implements MutableAccessControlEntry {

    public static AccessControlEntrySupport of(AccessControlEntry ace) {
        return (AccessControlEntrySupport) ace;
    }

    @Override
    public void addPermission(Permission permission) {
        if (!this.checkPermission(permission)) {
            this.getPermissions().add(permission);
        }
    }

    @Override
    public void addPermissions(Set<Permission> permissions) {
        SetUtils.emptyIfNull(permissions).forEach(this::addPermission);
    }

    @Override
    public void removePermission(Permission permission) {
        this.getPermissions().remove(permission);
    }

    @Override
    public void removePermissions(Set<Permission> permissions) {
        SetUtils.emptyIfNull(permissions).forEach(this::removePermission);
    }

    @Override
    public boolean checkPermission(Permission permission) {
        return this.getPermissions().contains(permission);
    }

    @Override
    public boolean checkAnyPermission(Set<Permission> permissions) {
        return CollectionUtils.containsAny(this.getPermissions(), permissions);
    }
}
