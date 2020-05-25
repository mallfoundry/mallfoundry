package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.InternalPrincipal;
import org.mallfoundry.security.acl.PrincipalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaAclPrincipalRepository")
public interface JpaPrincipalRepository extends
        JpaRepository<InternalPrincipal, String>, PrincipalRepository {
}
