package org.mallfoundry.security.acl;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;

/**
 * 访问控制列表中的 {@code Principal} 代表。
 */
public interface Principal extends Serializable {

    String USER_TYPE = "User";

    String AUTHORITY_TYPE = "Authority";

    String getName();

    String getType();

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Principal> {

        Builder name(String name);

        Builder type(String type);
    }
}
