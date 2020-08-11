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

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultAccessControlManager implements AccessControlManager {

    static final String AC_ID_VALUE_NAME = "access.control.id";

    static final String ENTRY_ID_VALUE_NAME = "access.control.entry.id";

    static final String PRINCIPAL_ID_VALUE_NAME = "access.principal.id";

    static final String RESOURCE_ID_VALUE_NAME = "access.resource.id";

    private final PrincipalRepository principalRepository;

    private final ResourceRepository resourceRepository;

    private final AccessControlRepository accessControlRepository;

    public DefaultAccessControlManager(PrincipalRepository principalRepository,
                                       ResourceRepository resourceRepository,
                                       AccessControlRepository accessControlRepository) {
        this.principalRepository = principalRepository;
        this.resourceRepository = resourceRepository;
        this.accessControlRepository = accessControlRepository;
    }

    @Override
    public Principal createPrincipal(String type, String name) {
        return this.principalRepository.create(null).toBuilder().type(type).name(name).build();
    }

    @Override
    public Principal addPrincipal(Principal principal) {
        return this.principalRepository.findByTypeAndName(principal.getType(), principal.getName())
                .orElseGet(() -> this.principalRepository.save(principal.toBuilder().id(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME)).build()));
    }

    @Override
    public List<Principal> addPrincipals(List<Principal> principals) {
        principals = principals.stream()
                .map(principal -> principal.toBuilder().id(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME)).build())
                .collect(Collectors.toUnmodifiableList());
        return this.principalRepository.saveAll(principals);
    }

    @Override
    public Optional<Principal> findPrincipal(Principal principal) {
        return this.principalRepository.findByTypeAndName(principal.getType(), principal.getName());
    }

    @Override
    public Principal getPrincipal(Principal principal) {
        return this.findPrincipal(principal).orElseThrow();
    }

    @Override
    public void removePrincipal(Principal principal) {
        this.principalRepository.delete(principal);
    }

    @Override
    public void removePrincipals(List<Principal> principals) {
        this.principalRepository.deleteAll(principals);
    }

    @Override
    public Resource createResource(Object object) {
        Assert.notNull(object, "The object must not be null");
        return this.resourceRepository.create(null, object);
    }

    @Override
    public Resource createResource(String type, Serializable identifier) {
        Assert.notNull(identifier, "The Resource identifier must not be null");
        return this.resourceRepository.create(null, type, String.valueOf(identifier));
    }

    private Resource addNewResource(Resource resource) {
        var mutableResource = (MutableResource) resource;
        if (Objects.isNull(mutableResource.getId())) {
            mutableResource.setId(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME));
        }
        return this.resourceRepository.save(mutableResource);
    }

    @Transactional
    @Override
    public Resource addResource(Resource resource) {
        return this.resourceRepository.findByTypeAndIdentifier(resource.getType(), resource.getIdentifier())
                .orElseGet(() -> this.addNewResource(resource));
    }

    @Transactional(readOnly = true)
    @Override
    public Resource getResource(Resource resource) {
        return this.findResource(resource).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Resource> findResource(Resource resource) {
        return this.resourceRepository.findByTypeAndIdentifier(resource.getType(), resource.getIdentifier());
    }

    @Transactional
    @Override
    public void removeResource(Resource resource) {
        resource = this.findResource(resource).orElse(null);
        if (Objects.nonNull(resource)) {
            this.findAccessControl(resource).ifPresent(this.accessControlRepository::delete);
            this.resourceRepository.delete(resource);
        }
    }

    @Override
    public Permission createPermission(String mask) {
        return new ImmutablePermission(mask);
    }

    @Override
    public AccessControl createAccessControl(Principal owner, Resource resource) {
        var acl = (MutableAccessControl) this.accessControlRepository.create(null);
        acl.setOwner(owner);
        acl.setResource(resource);
        return acl;
    }

    @Override
    public AccessControl addAccessControl(AccessControl accessControl) {
        Assert.notNull(accessControl.getOwner(), "The owner must not be null");
        Assert.isInstanceOf(MutableAccessControl.class, accessControl);
        return this.addAccessControl((MutableAccessControl) accessControl);
    }

    public AccessControl addAccessControl(MutableAccessControl accessControl) {
        this.setIdentity(accessControl);
        return this.accessControlRepository.save(accessControl);
    }

    @Override
    public Optional<AccessControl> findAccessControl(Resource resource) {
        return this.accessControlRepository.findByResource(resource);
    }

    @Override
    public Optional<AccessControl> findAccessControl(Resource resource, Set<Principal> principals) {
        return this.accessControlRepository.findByResourceAndPrincipals(resource, principals);
    }

    @Override
    public AccessControl getAccessControl(Resource resource) {
        return this.findAccessControl(resource).orElseThrow();
    }

    @Override
    public AccessControl getAccessControl(Resource resource, Set<Principal> principals) {
        return this.findAccessControl(resource, principals).orElseThrow();
    }

    @Transactional
    @Override
    public void grantPermission(Permission permission, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource);
        accessControl.grant(permission, principal);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void grantPermissions(Set<Permission> permissions, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource);
        accessControl.grants(permissions, principal);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void revokePermission(Permission permission, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource);
        accessControl.revoke(permission, principal);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void revokePermissions(Set<Permission> permissions, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource);
        accessControl.revoke(permissions, principal);
        this.updateAccessControl(accessControl);
    }

    private void updateAccessControl(AccessControl accessControl) {
        Assert.notNull(accessControl.getOwner(), "The owner must not be null");
        Assert.isInstanceOf(MutableAccessControl.class, accessControl);
        this.updateAccessControl((MutableAccessControl) accessControl);
    }

    private void updateAccessControl(MutableAccessControl accessControl) {
        this.setIdentity(accessControl);
        this.accessControlRepository.save(accessControl);
    }

    private void setIdentity(MutableAccessControl accessControl) {
        if (Objects.isNull(accessControl.getId())) {
            accessControl.setId(PrimaryKeyHolder.next(AC_ID_VALUE_NAME));
        }
        var resource = (MutableResource) accessControl.getResource();
        if (Objects.isNull(resource.getId())) {
            resource.setId(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME));
            accessControl.setResource(resource);
        }
        var owner = (MutablePrincipal) accessControl.getOwner();
        if (Objects.isNull(owner.getId())) {
            owner.setId(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME));
        }
        accessControl.getEntries().stream()
                .map(AccessControlEntrySupport::of)
                .peek(entry -> {
                    var principal = (MutablePrincipal) entry.getPrincipal();
                    if (Objects.isNull(principal.getId())) {
                        principal.setId(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME));
                    }
                })
                .filter(entry -> Objects.isNull(entry.getId()))
                .forEach(entry -> entry.setId(PrimaryKeyHolder.next(ENTRY_ID_VALUE_NAME)));
    }
}
