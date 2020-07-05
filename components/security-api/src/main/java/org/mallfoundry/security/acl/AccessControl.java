package org.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface AccessControl extends Serializable {

    Resource getResource();

    Principal getOwner();

    AccessControl getParent();

    boolean isInherit();

    void setInherit(boolean inherit);

    List<AccessControlEntry> getEntries();

    void grant(Principal principal, Permission permission);

    void grants(Principal principal, Set<Permission> permissions);

    void revoke(Principal principal, Permission permission);

    void revoke(Principal principal, Set<Permission> permissions);

    boolean granted(Principal principal, Permission permission);

    boolean granted(Principal principal, Set<Permission> permissions);

    boolean granted(Set<Principal> principals, Set<Permission> permissions);
}
