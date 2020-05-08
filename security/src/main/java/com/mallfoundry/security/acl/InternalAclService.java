package com.mallfoundry.security.acl;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Optional;

@Service
public class InternalAclService implements AclService {

    static final String PRINCIPAL_ID_VALUE_NAME = "acl.principal.id";
    static final String RESOURCE_ID_VALUE_NAME = "acl.resource.id";

    @Override
    public Principal createPrincipal(String type, String name) {
        return new InternalPrincipal(PrimaryKeyHolder.next(PRINCIPAL_ID_VALUE_NAME), type, name);
    }

    @Override
    public Resource createResource(Object object) {
        Assert.notNull(object, "Resource must not be null");
        return new InternalResource(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME), object);
    }

    @Override
    public Resource createResource(String type, Serializable identifier) {
        Assert.notNull(identifier, "Resource id must not be null");
        return new InternalResource(PrimaryKeyHolder.next(RESOURCE_ID_VALUE_NAME), type, String.valueOf(identifier));
    }

    @Override
    public Permission createPermission(String mask) {
        return new InternalPermission(mask);
    }

    @Override
    public Acl createAcl(Resource resource) {
        return null;
    }

    @Override
    public Optional<Acl> getAcl(Resource resource) {
        return Optional.empty();
    }


    @Override
    public Acl saveAcl(Acl acl) {
        return null;
    }
}
