package org.mallfoundry.security.acl;

import java.util.Optional;

public interface ResourceRepository {

    MutableResource create(String id);

    MutableResource create(String id, Object resource);

    MutableResource create(String id, String identifier, String type);

    MutableResource save(MutableResource resource);

    Optional<MutableResource> findByTypeAndIdentifier(String type, String identifier);
}
