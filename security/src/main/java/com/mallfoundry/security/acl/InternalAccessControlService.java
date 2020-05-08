package com.mallfoundry.security.acl;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InternalAccessControlService implements AccessControlService {

    static final String AC_ID_VALUE_NAME = "access.control.id";

    static final String ENTRY_ID_VALUE_NAME = "access.control.entry.id";

    static final String PRINCIPAL_ID_VALUE_NAME = "access.principal.id";

    static final String RESOURCE_ID_VALUE_NAME = "access.resource.id";

    private final AccessControlRepository accessControlRepository;

    private final AccessControlEntryRepository accessControlEntryRepository;

    private final PrincipalRepository principalRepository;

    private final ResourceRepository resourceRepository;

    public InternalAccessControlService(AccessControlRepository accessControlRepository,
                                        AccessControlEntryRepository accessControlEntryRepository,
                                        PrincipalRepository principalRepository,
                                        ResourceRepository resourceRepository) {
        this.accessControlRepository = accessControlRepository;
        this.accessControlEntryRepository = accessControlEntryRepository;
        this.principalRepository = principalRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Principal createPrincipal(String type, String name) {
        return this.principalRepository.findByTypeAndName(type, name)
                .orElseGet(() -> new InternalPrincipal(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME), type, name));
    }

    @Override
    public Resource createResource(Object object) {
        Assert.notNull(object, "The object must not be null");
        return this.createOrGetResource(new InternalResource(null, object));
    }

    @Override
    public Resource createResource(String type, Serializable identifier) {
        Assert.notNull(identifier, "The Resource identifier must not be null");
        return this.createOrGetResource(new InternalResource(null, type, String.valueOf(identifier)));
    }

    public Resource createOrGetResource(InternalResource resource) {
        return this.resourceRepository.findByTypeAndIdentifier(resource.getType(), resource.getIdentifier())
                .orElseGet(() -> {
                    resource.setId(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME));
                    return resource;
                });
    }

    @Override
    public Permission createPermission(String mask) {
        return new InternalPermission(mask);
    }

    @Override
    public AccessControl createAccessControl(Principal owner, Resource resource) {
        var acl = new InternalAccessControl();
        acl.setId(PrimaryKeyHolder.next(AC_ID_VALUE_NAME));
        acl.setOwner(owner);
        acl.setResource(InternalResource.of(resource));
        return this.accessControlRepository.save(acl);
    }

    @Override
    public Optional<AccessControl> getAccessControl(Resource resource) {
        return CastUtils.cast(this.accessControlRepository.findByResource(resource));
    }

    @Override
    public Optional<AccessControl> getAccessControl(Resource resource, List<Principal> principals) {
        return CastUtils.cast(this.accessControlRepository.findByResource(resource));
    }

    @Transactional
    @Override
    public AccessControl saveAccessControl(AccessControl accessControl) {
        Assert.notNull(accessControl.getOwner(), "The owner must not be null");
        InternalAccessControl internalAcl = InternalAccessControl.of(accessControl);
        internalAcl.getEntries().stream()
                .map(InternalAccessControlEntry::of)
                .filter(entry -> Objects.isNull(entry.getId()))
                .forEach(entry -> entry.setId(PrimaryKeyHolder.next(ENTRY_ID_VALUE_NAME)));
        return CastUtils.cast(this.accessControlRepository.save(internalAcl));
    }
}
