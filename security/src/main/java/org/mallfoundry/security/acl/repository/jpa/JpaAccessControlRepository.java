package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.AccessControlRepository;
import org.mallfoundry.security.acl.InternalAccessControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessControlRepository extends
        JpaRepository<InternalAccessControl, String>, AccessControlRepository {
}
