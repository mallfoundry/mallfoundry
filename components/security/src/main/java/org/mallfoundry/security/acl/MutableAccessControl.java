package org.mallfoundry.security.acl;

public interface MutableAccessControl extends AccessControl {

    String getId();

    void setId(String id);

    void setOwner(Principal owner);

    void setResource(Resource resource);
}
