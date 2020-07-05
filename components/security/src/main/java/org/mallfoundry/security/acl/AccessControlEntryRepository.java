package org.mallfoundry.security.acl;

import org.mallfoundry.security.acl.repository.jpa.JpaAccessControlEntry;

import java.util.List;

public interface AccessControlEntryRepository {
    List<JpaAccessControlEntry> findAllByPrincipalIn(List<Principal> principals);
}
