package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.MutablePrincipal;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.PrincipalRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaPrincipalRepository implements PrincipalRepository {

    private final JpaPrincipalRepositoryDelegate repository;

    public JpaPrincipalRepository(JpaPrincipalRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public MutablePrincipal create(String id) {
        return new JpaPrincipal(id);
    }

    @Override
    public Principal save(Principal principal) {
        return this.repository.save(JpaPrincipal.of(principal));
    }

    @Override
    public Optional<Principal> findByTypeAndName(String type, String name) {
        return CastUtils.cast(this.repository.findByTypeAndName(type, name));
    }
}
