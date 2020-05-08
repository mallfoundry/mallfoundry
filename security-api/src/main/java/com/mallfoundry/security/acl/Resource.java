package com.mallfoundry.security.acl;

import java.io.Serializable;

public interface Resource extends Serializable {

    String getIdentifier();

    String getType();
}
