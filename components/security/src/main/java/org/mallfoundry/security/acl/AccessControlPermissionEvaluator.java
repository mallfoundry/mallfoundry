package org.mallfoundry.security.acl;

import org.apache.commons.collections4.SetUtils;
import org.mallfoundry.security.Subject;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 评估给定用户对资源是否拥有访问权限。
 *
 * @author Zhi Tang
 */
public class AccessControlPermissionEvaluator implements PermissionEvaluator {

    private final AccessControlManager manager;

    private final AccessControlAuthorizer authorizer;

    public AccessControlPermissionEvaluator(AccessControlManager manager, AccessControlAuthorizer authorizer) {
        this.manager = manager;
        this.authorizer = authorizer;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        Resource resource = this.manager.getResource(target).orElseThrow();
        return this.hasPermission(authentication, resource, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Resource resource = this.manager.getResource(targetType, targetId).orElseThrow();
        return this.hasPermission(authentication, resource, permission);
    }

    private boolean hasPermission(Authentication authentication, Resource resource, Object permission) {
        var principals = this.getPrincipals(authentication);
        return this.authorizer.hasAnyPermissions(principals, resource, this.createPermissions(permission));
    }

    private Set<Permission> createPermissions(Object object) {
        return StringUtils.commaDelimitedListToSet(Objects.toString(object))
                .stream().map(this.manager::createPermission)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<Principal> getPrincipals(Authentication authentication) {
        Principal primaryPrincipal = this.createPrimaryPrincipal(authentication.getPrincipal());
        Set<Principal> authorityPrincipals = this.createAuthorityPrincipals(authentication.getAuthorities());
        return SetUtils.union(Set.of(primaryPrincipal), authorityPrincipals);
    }

    private Principal createPrimaryPrincipal(Object principal) {
        if (Objects.isNull(principal)) {
            return this.manager.createPrincipal(Principal.USER_TYPE, "anonymous");
        } else if (principal instanceof Subject) {
            return this.manager.createPrincipal(Principal.USER_TYPE, ((Subject) principal).getId());
        } else if (principal instanceof UserDetails) {
            return this.manager.createPrincipal(Principal.USER_TYPE, ((UserDetails) principal).getUsername());
        }
        return this.manager.createPrincipal(Principal.USER_TYPE, Objects.toString(principal));
    }

    private <E extends GrantedAuthority> Set<Principal> createAuthorityPrincipals(Collection<E> authorities) {
        return authorities.stream().map(authority -> this.manager.createPrincipal(Principal.AUTHORITY_TYPE, authority.getAuthority()))
                .collect(Collectors.toUnmodifiableSet());
    }
}
