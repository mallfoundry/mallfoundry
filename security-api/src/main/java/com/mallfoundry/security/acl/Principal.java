package com.mallfoundry.security.acl;

import java.io.Serializable;

public interface Principal extends Serializable {

    String getName();

    String getType();
}
