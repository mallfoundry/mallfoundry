package org.mallfoundry.security;


import org.mallfoundry.security.acl.AccessControlAuthorizer;
import org.mallfoundry.security.acl.AccessControlManager;
import org.mallfoundry.security.acl.AccessControlPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@Configuration
public class SecurityAccessControlConfiguration extends GlobalMethodSecurityConfiguration {

    @Bean
    public AccessControlPermissionEvaluator accessControlPermissionEvaluator(AccessControlManager manager,
                                                                             AccessControlAuthorizer authorizer) {
        return new AccessControlPermissionEvaluator(manager, authorizer);
    }
}
