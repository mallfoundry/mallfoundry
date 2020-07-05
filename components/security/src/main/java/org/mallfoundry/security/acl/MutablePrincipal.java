package org.mallfoundry.security.acl;

public interface MutablePrincipal extends Principal {

    String getId();

    void setId(String id);

    void setName(String name);

    void setType(String type);
}
