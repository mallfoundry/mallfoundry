package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.MutableResource;
import org.mallfoundry.security.acl.ResourceRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaResourceRepository implements ResourceRepository {

    private final JpaResourceRepositoryDelegate repository;

    public JpaResourceRepository(JpaResourceRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public MutableResource create(String id) {
        return new JpaResource(id);
    }

    @Override
    public MutableResource create(String id, Object resource) {
        return new JpaResource(id, resource);
    }

    @Override
    public MutableResource create(String id, String identifier, String type) {
        return new JpaResource(id, identifier, type);
    }

    @Override
    public MutableResource save(MutableResource resource) {
        return this.repository.save(JpaResource.of(resource));
    }

    @Override
    public Optional<MutableResource> findByTypeAndIdentifier(String type, String identifier) {
        return CastUtils.cast(this.repository.findByTypeAndIdentifier(type, identifier));
    }
}
