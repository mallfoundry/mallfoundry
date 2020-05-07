package com.mallfoundry;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class  AclConfiguration {

    @Bean
    public AuditLogger auditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy(AuditLogger auditLogger) {
        return new DefaultPermissionGrantingStrategy(auditLogger);
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        var a1 = new SimpleGrantedAuthority("ADMIN");
//        var a2 = new SimpleGrantedAuthority("gaModifyAuditing");
//        var a3 = new SimpleGrantedAuthority("gaGeneralChanges");
        return new AclAuthorizationStrategyImpl(a1/*, a2, a3*/);
    }

    @Bean
    public AclCache aclCache(CacheManager cacheManager,
                             PermissionGrantingStrategy permissionGrantingStrategy,
                             AclAuthorizationStrategy aclAuthorizationStrategy) {
        return new SpringCacheBasedAclCache(Objects.requireNonNull(cacheManager.getCache("aclCache")), permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    public LookupStrategy lookupStrategy(DataSource dataSource,
                                         AclCache aclCache,
                                         AclAuthorizationStrategy aclAuthorizationStrategy,
                                         PermissionGrantingStrategy permissionGrantingStrategy) {
        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, permissionGrantingStrategy);
    }

    @Bean
    public MutableAclService aclService(DataSource dataSource,
                                        LookupStrategy lookupStrategy,
                                        AclCache aclCache) {
        var aclService = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);

        aclService.setClassIdentityQuery("select currval('acl_class_id_seq')");
        aclService.setSidIdentityQuery("select currval('acl_sid_id_seq')");
        return aclService;
    }

}
