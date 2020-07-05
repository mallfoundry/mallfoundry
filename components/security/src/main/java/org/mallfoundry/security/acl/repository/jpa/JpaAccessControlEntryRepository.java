package org.mallfoundry.security.acl.repository.jpa;

import org.mallfoundry.security.acl.AccessControlEntryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessControlEntryRepository extends
        JpaRepository<JpaAccessControlEntry, String>, AccessControlEntryRepository {
}
