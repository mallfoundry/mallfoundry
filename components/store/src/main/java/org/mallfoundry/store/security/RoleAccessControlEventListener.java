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

package org.mallfoundry.store.security;

import org.mallfoundry.security.access.AccessControlManager;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RoleAccessControlEventListener {

    private final AccessControlManager manager;

    public RoleAccessControlEventListener(AccessControlManager manager) {
        this.manager = manager;
    }

    private Set<String> createPermissions(Role role) {
        return role.getAuthorities().stream().collect(Collectors.toUnmodifiableSet());
    }

    @Transactional
    @EventListener
    public void onRoleAdded(RoleAddedEvent event) {
        var role = event.getRole();
        var rolePrincipal = this.manager.createPrincipal(Principal.AUTHORITY_TYPE, Roles.getRoleAuthority(role));
        var resource = this.manager.createResource(Resource.STORE_TYPE, role.getStoreId());
        var accessControl = this.manager.createAccessControl(resource);
        accessControl.grant(this.createPermissions(role), rolePrincipal);
        this.manager.grantAccessControl(accessControl);
    }

    @Transactional
    @EventListener
    public void onRoleChanged(RoleChangedEvent event) {
        var role = event.getRole();
        var rolePrincipal = this.manager.createPrincipal(Principal.AUTHORITY_TYPE, Roles.getRoleAuthority(role));
        var resource = this.manager.createResource(Resource.STORE_TYPE, role.getStoreId());
        var accessControl = this.manager.createAccessControl(resource);
        accessControl.grant(this.createPermissions(role), rolePrincipal);
        this.manager.updateAccessControl(accessControl);
    }


    @Transactional
    @EventListener
    public void onRoleDeleted(RoleDeletedEvent event) {
        var role = event.getRole();
        var rolePrincipal = this.manager.createPrincipal(Principal.AUTHORITY_TYPE, Roles.getRoleAuthority(role));
        this.manager.removePrincipal(rolePrincipal);
    }

    @Transactional
    @EventListener
    public void onRolesCleared(RolesClearedEvent event) {
        var rolePrincipals = event.getRoles().stream()
                .map(role -> this.manager.createPrincipal(Principal.AUTHORITY_TYPE, Roles.getRoleAuthority(role)))
                .collect(Collectors.toUnmodifiableList());
        this.manager.removePrincipals(rolePrincipals);
    }
}
