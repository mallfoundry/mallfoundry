package com.mallfoundry.security.acl;

import java.io.Serializable;
import java.util.List;

public interface Acl extends Serializable {

    Resource getResource();

    Principal getOwner();

    Acl getParent();

    boolean isInherit();

    void setInherit(boolean inherit);

    List<AclEntry> getEntries();

    void grant(Principal principal, Permission permission);

    void revoke(Principal principal, Permission permission);

    boolean granted(Principal principal, Permission permission);

    boolean granted(Principal principal, List<Permission> permissions);

    boolean granted(List<Principal> principals, List<Permission> permissions);
}
