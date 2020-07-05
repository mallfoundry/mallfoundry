package org.mallfoundry.security.acl;

public interface MutableResource extends Resource {

    String getId();

    void setId(String id);

    void setIdentifier(String identifier);

    void setType(String type);

}
