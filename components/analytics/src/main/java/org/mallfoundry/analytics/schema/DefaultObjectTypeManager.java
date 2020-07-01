package org.mallfoundry.analytics.schema;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultObjectTypeManager implements ObjectTypeManager {

    private final ObjectTypeRepository repository;

    public DefaultObjectTypeManager(ObjectTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ObjectType createObjectType(String typeId) {
        return this.repository.create(typeId);
    }

    @Transactional
    @Override
    public ObjectType addObjectType(ObjectType objectType) {
        return this.repository.save(objectType);
    }

    @Override
    public Optional<ObjectType> getObjectType(String typeId) {
        return this.repository.findById(typeId);
    }

    @Transactional
    @Override
    public void deleteObjectType(String typeId) {
        var objectType = this.repository.findById(typeId).orElseThrow();
        this.repository.delete(objectType);
    }
}
