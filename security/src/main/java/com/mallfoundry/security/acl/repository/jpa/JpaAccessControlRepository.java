package com.mallfoundry.security.acl.repository.jpa;

import com.mallfoundry.security.acl.AccessControlRepository;
import com.mallfoundry.security.acl.InternalAccessControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessControlRepository extends
        JpaRepository<InternalAccessControl, String>, AccessControlRepository {
}
