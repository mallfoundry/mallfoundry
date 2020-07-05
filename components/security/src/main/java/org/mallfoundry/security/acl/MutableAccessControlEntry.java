package org.mallfoundry.security.acl;

import java.util.Set;

public interface MutableAccessControlEntry extends AccessControlEntry {

    String getId();

    void setId(String id);

    void setPrincipal(Principal principal);

    void setPermissions(Set<Permission> permissions);
}
