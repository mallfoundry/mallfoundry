package org.mallfoundry.analytics.schema;

import org.mallfoundry.util.Positions;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class ObjectTypeSupport implements ObjectType {

    @Override
    public ObjectField createField(String name) {
        return new DefaultObjectField(name);
    }

    @Override
    public void addField(ObjectField field) {
        if (this.getFields().contains(field)) {
            throw new SchemaException("Field ex");
        }
        this.getFields().add(field);
        Positions.sort(this.getFields());
    }

    @Override
    public Optional<ObjectField> getField(String name) throws SchemaException {
        return this.getFields().stream()
                .filter(field -> Objects.equals(field.getName(), name))
                .findFirst();
    }

    @Override
    public void setField(ObjectField field) {
        var oldField = this.getField(field.getName()).orElseThrow();
        if (Objects.nonNull(field.getLabel())) {
            oldField.setLabel(field.getLabel());
        }
        if (Objects.nonNull(field.getType())) {
            oldField.setType(field.getType());
        }
    }

    @Override
    public void removeField(ObjectField field) {
        this.getFields().remove(field);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ObjectTypeSupport objectType;

        BuilderSupport(ObjectTypeSupport objectType) {
            this.objectType = objectType;
        }

        @Override
        public Builder name(String name) {
            this.objectType.setName(name);
            return this;
        }

        @Override
        public Builder addField(ObjectField field) {
            this.objectType.addField(field);
            return this;
        }

        @Override
        public Builder addField(Function<ObjectType, ObjectField> function) {
            return this.addField(function.apply(this.objectType));
        }

        @Override
        public ObjectType build() {
            return this.objectType;
        }
    }
}
