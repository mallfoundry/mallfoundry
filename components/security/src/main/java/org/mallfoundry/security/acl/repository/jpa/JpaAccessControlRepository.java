package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.AccessControl;
import org.mallfoundry.security.acl.AccessControlRepository;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.Resource;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class JpaAccessControlRepository implements AccessControlRepository {

    private final JpaAccessControlRepositoryDelegate repository;

    public JpaAccessControlRepository(JpaAccessControlRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public AccessControl create(String id) {
        return new JpaAccessControl(id);
    }

    @Override
    public Optional<AccessControl> findByResource(Resource resource) {
        return CastUtils.cast(repository.findByResource(resource));
    }

    @Override
    public Optional<AccessControl> findByResourceAndPrincipals(Resource resource, Set<Principal> principals) {
        return CastUtils.cast(this.repository.findByResourceAndPrincipals(resource, principals));
    }

    @Override
    public AccessControl save(AccessControl control) {
        return this.repository.save(JpaAccessControl.of(control));
    }
}
