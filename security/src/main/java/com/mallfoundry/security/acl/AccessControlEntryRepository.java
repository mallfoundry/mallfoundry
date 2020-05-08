package com.mallfoundry.security.acl;

import java.util.List;

public interface AccessControlEntryRepository {
    List<InternalAccessControlEntry> findAllByPrincipalIn(List<Principal> principals);
}
