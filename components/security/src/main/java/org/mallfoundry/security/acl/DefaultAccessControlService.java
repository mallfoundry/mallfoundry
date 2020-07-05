package org.mallfoundry.security.acl;

import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.security.acl.repository.jpa.JpaAccessControl;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultAccessControlService implements AccessControlManager {

    static final String AC_ID_VALUE_NAME = "access.control.id";

    static final String ENTRY_ID_VALUE_NAME = "access.control.entry.id";

    static final String PRINCIPAL_ID_VALUE_NAME = "access.principal.id";

    static final String RESOURCE_ID_VALUE_NAME = "access.resource.id";

    private final PrincipalRepository principalRepository;

    private final ResourceRepository resourceRepository;

    private final AccessControlRepository accessControlRepository;

    public DefaultAccessControlService(PrincipalRepository principalRepository,
                                       ResourceRepository resourceRepository,
                                       AccessControlRepository accessControlRepository) {
        this.principalRepository = principalRepository;
        this.resourceRepository = resourceRepository;
        this.accessControlRepository = accessControlRepository;
    }

    @Override
    public Principal createPrincipal(String type, String name) {
        var principal = this.principalRepository.create(null);
        principal.setType(type);
        principal.setName(name);
        return principal;
    }

    @Override
    public Principal addPrincipal(Principal principal) {
        Assert.isInstanceOf(MutablePrincipal.class, principal);
        var mutablePrincipal = (MutablePrincipal) principal;
        if (Objects.isNull(mutablePrincipal.getId())) {
            mutablePrincipal.setId(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME));
        }
        return this.principalRepository.save(principal);
    }

    @Override
    public Principal addPrincipal(String type, String name) {
        return this.addPrincipal(
                this.principalRepository.create(null)
                        .toBuilder().type(type).name(name).build());
    }

    @Override
    public Optional<Principal> getPrincipal(String type, String name) {
        return CastUtils.cast(this.principalRepository.findByTypeAndName(type, name));
    }

    @Override
    public Resource createResource(Object object) {
        Assert.notNull(object, "The object must not be null");
        return new InternalResource(null, object);
    }

    @Override
    public Resource createResource(String type, Serializable identifier) {
        Assert.notNull(identifier, "The Resource identifier must not be null");
        return new InternalResource(null, type, String.valueOf(identifier));
    }

    @Override
    public Optional<Resource> getResource(Object object) {
        var resource = this.createResource(object);
        return this.getResource(resource.getType(), resource.getIdentifier());
    }

    @Override
    public Optional<Resource> getResource(String type, Serializable identifier) {
        return CastUtils.cast(this.resourceRepository.findByTypeAndIdentifier(type, Objects.toString(identifier)));
    }

    @Override
    public Permission createPermission(String mask) {
        return new ImmutablePermission(mask);
    }

    @Override
    public AccessControl createAccessControl(Principal owner, Resource resource) {
        var acl = (MutableAccessControl) this.accessControlRepository.create(null);
        acl.setOwner(owner);
        acl.setResource(InternalResource.of(resource));
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
    public Optional<AccessControl> getAccessControl(Resource resource) {
        return this.accessControlRepository.findByResource(resource);
    }

    @Override
    public Optional<AccessControl> getAccessControl(Resource resource, Set<Principal> principals) {
        return this.accessControlRepository.findByResourceAndPrincipals(resource, principals);
    }

    @Transactional
    @Override
    public void grantPermission(Permission permission, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource).orElseThrow();
        accessControl.grant(principal, permission);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void grantPermissions(Set<Permission> permissions, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource).orElseThrow();
        accessControl.grants(principal, permissions);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void revokePermission(Permission permission, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource).orElseThrow();
        accessControl.revoke(principal, permission);
        this.updateAccessControl(accessControl);
    }

    @Transactional
    @Override
    public void revokePermissions(Set<Permission> permissions, Resource resource, Principal principal) {
        var accessControl = this.getAccessControl(resource).orElseThrow();
        accessControl.revoke(principal, permissions);
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
        var resource = InternalResource.of(accessControl.getResource());
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
