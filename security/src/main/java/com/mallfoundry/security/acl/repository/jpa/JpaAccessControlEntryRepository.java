package com.mallfoundry.security.acl.repository.jpa;

import com.mallfoundry.security.acl.AccessControlEntryRepository;
import com.mallfoundry.security.acl.InternalAccessControlEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessControlEntryRepository extends
        JpaRepository<InternalAccessControlEntry, String>, AccessControlEntryRepository {

}
