package org.mallfoundry.security.acl;

import java.util.Optional;

public interface ResourceRepository {

    Optional<InternalResource> findByTypeAndIdentifier(String type, String identifier);
}
