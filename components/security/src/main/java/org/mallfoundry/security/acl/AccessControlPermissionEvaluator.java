/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
        var resource = this.manager.createResource(target);
        return this.hasPermission(authentication, resource.getIdentifier(), resource.getType(), permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Resource resource = this.manager.getResource(targetType, targetId).orElse(null);
        if (Objects.isNull(resource)) {
            return false;
        }
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
            return this.manager.getPrincipal(Principal.USER_TYPE, "anonymousUser").orElse(null);
        } else if (principal instanceof Subject) {
            return this.manager.getPrincipal(Principal.USER_TYPE, ((Subject) principal).getId()).orElse(null);
        } else if (principal instanceof UserDetails) {
            return this.manager.getPrincipal(Principal.USER_TYPE, ((UserDetails) principal).getUsername()).orElse(null);
        }
        return this.manager.getPrincipal(Principal.USER_TYPE, Objects.toString(principal)).orElse(null);
    }

    private <E extends GrantedAuthority> Set<Principal> createAuthorityPrincipals(Collection<E> authorities) {
        return authorities.stream()
                .map(authority -> this.manager.getPrincipal(Principal.AUTHORITY_TYPE, authority.getAuthority()))
                .map(op -> op.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
    }
}
