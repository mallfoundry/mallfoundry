package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPrincipalRepositoryDelegate extends JpaRepository<JpaPrincipal, String> {

    Optional<Principal> findByTypeAndName(String type, String name);
}
