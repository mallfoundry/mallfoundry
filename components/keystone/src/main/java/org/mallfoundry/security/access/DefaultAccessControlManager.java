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

    private Principal createNewPrincipal(Principal principal) {
        return this.principalRepository.save(
                principal.toBuilder().id(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME)).build());
    }

    public Principal requiredPrincipal(Principal principal) {
        return this.principalRepository.findByPrincipal(principal)
                .orElseGet(() -> this.createNewPrincipal(principal));
    }

    @Transactional
    @Override
    public void removePrincipal(Principal principal) {
        this.principalRepository.delete(principal);
    }

    @Transactional
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

    private Optional<Resource> findResource(Resource resource) {
        return this.resourceRepository.findByResource(resource);
    }

    private Resource getResource(Resource resource) {
        return this.findResource(resource)
                .orElseThrow(() -> new AccessException(AccessMessages.Resource.notFound(resource.getType(), resource.getIdentifier())));
    }

    private Resource createNewResource(Resource resource) {
        resource.setId(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME));
        return this.resourceRepository.save(resource);
    }

    private Resource requiredResource(Resource resource) {
        if (Objects.isNull(resource.getId())) {
            final var newResource = resource;
            resource = this.resourceRepository.findByResource(resource)
                    .orElseGet(() -> createNewResource(newResource));
        }
        return resource;
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
    public AccessControl createAccessControl(Resource resource) {
        return this.accessControlRepository.create(null, resource);
    }

    @Override
    public AccessControl createAccessControl(Principal owner, Resource resource) {
        return this.accessControlRepository.create(owner, resource);
    }

    @Transactional
    @Override
    public AccessControl addAccessControl(AccessControl accessControl) {
        accessControl.setResource(this.requiredResource(accessControl.getResource()));
        accessControl.setOwner(this.requiredPrincipal(accessControl.getOwner()));
        var existsAccessControl = this.findAccessControl(accessControl.getResource()).orElse(null);
        if (Objects.nonNull(existsAccessControl)) {
            return existsAccessControl;
        }
        this.setIdentity(accessControl);
        return this.accessControlRepository.save(accessControl);
    }

    private Optional<AccessControl> findAccessControl(Resource resource) {
        if (Objects.isNull(resource.getId())) {
            resource = this.getResource(resource);
        }
        return this.accessControlRepository.findByResource(resource);
    }

    private Optional<AccessControl> findAccessControl(Resource resource, Set<Principal> principals) {
        if (Objects.isNull(resource.getId())) {
            resource = this.getResource(resource);
        }
        return this.accessControlRepository.findByResourceAndPrincipals(resource, principals);
    }

    @Override
    public AccessControl getAccessControl(Resource resource) {
        return this.findAccessControl(resource)
                .orElseThrow(() -> new AccessException(AccessMessages.AccessControl.notFound(resource.getType(), resource.getIdentifier())));
    }

    @Override
    public AccessControl getAccessControl(Resource resource, Set<Principal> principals) {
        return this.findAccessControl(resource, principals)
                .orElseThrow(() -> new AccessException(AccessMessages.AccessControl.notFound(resource.getType(), resource.getIdentifier())));
    }

    @Transactional
    @Override
    public void grantAccessControl(AccessControl source) {
        var accessControl = this.getAccessControl(source.getResource());
        source.getEntries().forEach(entry -> accessControl.grant(entry.getPermissions(), entry.getPrincipal()));
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void revokeAccessControl(AccessControl source) {
        var accessControl = this.getAccessControl(source.getResource());
        source.getEntries().forEach(entry -> accessControl.revoke(entry.getPermissions(), entry.getPrincipal()));
        this.updateAccessControl(accessControl);
    }

    private void updateAccessControl(AccessControl accessControl) {
        Assert.notNull(accessControl.getOwner(), "The owner must not be null");
        this.setIdentity(accessControl);
        this.accessControlRepository.save(accessControl);
    }

    private void setIdentity(AccessControl accessControl) {
        if (Objects.isNull(accessControl.getId())) {
            accessControl.setId(PrimaryKeyHolder.next(AC_ID_VALUE_NAME));
        }
        var resource = accessControl.getResource();
        if (Objects.isNull(resource.getId())) {
            resource.setId(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME));
            accessControl.setResource(resource);
        }
        var owner = accessControl.getOwner();
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

    @Transactional
    @Override
    public void deleteAccessControl(AccessControl accessControl) {
        this.findResource(accessControl.getResource())
                .flatMap(this.accessControlRepository::findByResource)
                .ifPresent(this.accessControlRepository::delete);
    }
}
