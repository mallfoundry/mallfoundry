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

package org.mallfoundry.security.access;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mallfoundry.i18n.MessageHolder.message;

@Service
public class SimpleAccessControlAuthorizer implements AccessControlAuthorizer {

    private final AccessControlManager manager;

    public SimpleAccessControlAuthorizer(AccessControlManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean hasPermission(Principal principal, Resource resource, Permission permission) {
        return this.hasPermission(Set.of(principal), resource, permission);
    }

    @Override
    public boolean hasPermission(Set<Principal> principals, Resource resource, Permission permission) {
        return this.hasAnyPermissions(principals, resource, Set.of(permission));
    }

    @Override
    public boolean hasAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) {
        return this.hasAnyPermissions(Set.of(principal), resource, permissions);
    }


    @Override
    public boolean hasAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) {
        resource = this.manager.getResource(resource.getType(), resource.getIdentifier()).orElse(null);
        if (Objects.isNull(resource)) {
            return false;
        }
        principals = principals.stream()
                .map(principal -> this.manager.getPrincipal(principal.getType(), principal.getName()))
                .map(op -> op.orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
        if (CollectionUtils.isEmpty(principals)) {
            return false;
        }
        var accessControl = this.manager.getAccessControl(resource, principals).orElse(null);
        if (Objects.isNull(accessControl)) {
            return false;
        }
        return accessControl.granted(permissions, principals);
    }

    @Override
    public void checkPermission(Principal principal, Resource resource, Permission permission) throws AccessDeniedException {
        if (!this.hasPermission(principal, resource, permission)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkPermission(Set<Principal> principals, Resource resource, Permission permission) throws AccessDeniedException {
        if (!this.hasPermission(principals, resource, permission)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkAnyPermissions(Principal principal, Resource resource, Set<Permission> permissions) throws AccessDeniedException {
        if (!this.hasAnyPermissions(principal, resource, permissions)) {
            this.denyAccess();
        }
    }

    @Override
    public void checkAnyPermissions(Set<Principal> principals, Resource resource, Set<Permission> permissions) throws AccessDeniedException {
        if (!this.hasAnyPermissions(principals, resource, permissions)) {
            this.denyAccess();
        }
    }

    private void denyAccess() {
        throw new AccessDeniedException(message("security.accessDenied", "Access is denied"));
    }
}
