package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface JpaAccessControlRepositoryDelegate extends JpaRepository<JpaAccessControl, String> {

    Optional<JpaAccessControl> findByResource(Resource resource);

    @Query("select ac from JpaAccessControl ac JOIN ac.entries ace where ac.resource = ?1 and ace.principal in (?2)")
    Optional<JpaAccessControl> findByResourceAndPrincipals(Resource resource, Set<Principal> principals);
}
