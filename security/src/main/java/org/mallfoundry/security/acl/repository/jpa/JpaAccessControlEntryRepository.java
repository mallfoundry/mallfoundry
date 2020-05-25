package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.AccessControlEntryRepository;
import org.mallfoundry.security.acl.InternalAccessControlEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessControlEntryRepository extends
        JpaRepository<InternalAccessControlEntry, String>, AccessControlEntryRepository {

}
