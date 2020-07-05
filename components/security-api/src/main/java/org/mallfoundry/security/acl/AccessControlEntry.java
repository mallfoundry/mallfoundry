package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.Set;

public interface AccessControlEntry extends Serializable {

    Principal getPrincipal();

    Set<Permission> getPermissions();

    void addPermission(Permission permission);

    void addPermissions(Set<Permission> permissions);

    void removePermission(Permission permission);

    void removePermissions(Set<Permission> permissions);

    boolean checkPermission(Permission permission);

    boolean checkAnyPermission(Set<Permission> permissions);
}
