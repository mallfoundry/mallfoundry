package com.mallfoundry.security.acl;

import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;

import javax.sql.DataSource;

public class JdbcMutableAclService extends org.springframework.security.acls.jdbc.JdbcMutableAclService {

    public JdbcMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
    }

    @Override
    protected Long createOrRetrieveClassPrimaryKey(String type, boolean allowCreate, Class idType) {
        return super.createOrRetrieveClassPrimaryKey(type, allowCreate, idType);
    }
}
