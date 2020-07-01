package org.mallfoundry.analytics.schema.repository.jpa;

import org.mallfoundry.analytics.schema.ObjectType;
import org.mallfoundry.analytics.schema.ObjectTypeRepository;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaObjectTypeRepository implements ObjectTypeRepository {

    private final JpaObjectTypeRepositoryDelegate repository;

    public JpaObjectTypeRepository(JpaObjectTypeRepositoryDelegate repository) {
        this.repository = repository;
    }

    @Override
    public ObjectType create(String id) {
        return new JpaObjectType(id);
    }

    @Override
    public ObjectType save(ObjectType objectType) {
        return this.repository.save(JpaObjectType.of(objectType));
    }

    @Override
    public Optional<ObjectType> findById(String id) {
        return CastUtils.cast(this.repository.findById(id));
    }

    @Override
    public void delete(ObjectType objectType) {
        this.repository.delete(JpaObjectType.of(objectType));
    }
}
