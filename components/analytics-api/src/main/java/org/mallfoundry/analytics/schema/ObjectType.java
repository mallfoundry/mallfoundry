package org.mallfoundry.analytics.schema;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.ObjectToBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ObjectType extends ObjectToBuilder<ObjectType.Builder> {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    List<ObjectField> getFields();

    ObjectField createField(String name) throws SchemaException;

    Optional<ObjectField> getField(String name) throws SchemaException;

    void addField(ObjectField field) throws SchemaException;

    void setField(ObjectField field) throws SchemaException;

    void removeField(ObjectField field) throws SchemaException;

    interface Builder extends ObjectBuilder<ObjectType> {

        Builder name(String name);

        Builder addField(ObjectField field);

        Builder addField(Function<ObjectType, ObjectField> function);
    }
}
