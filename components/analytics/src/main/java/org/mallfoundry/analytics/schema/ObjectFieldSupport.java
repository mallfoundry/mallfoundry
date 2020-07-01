package org.mallfoundry.analytics.schema;

public abstract class ObjectFieldSupport implements ObjectField {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ObjectFieldSupport field;

        BuilderSupport(ObjectFieldSupport field) {
            this.field = field;
        }

        @Override
        public Builder name(String name) {
            this.field.setName(name);
            return this;
        }

        @Override
        public Builder label(String label) {
            this.field.setLabel(label);
            return this;
        }

        @Override
        public Builder type(String type) {
            this.field.setType(type);
            return this;
        }

        @Override
        public ObjectField build() {
            return this.field;
        }
    }
}
