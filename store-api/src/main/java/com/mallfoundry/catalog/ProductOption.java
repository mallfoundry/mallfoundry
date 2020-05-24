package com.mallfoundry.catalog;

import com.mallfoundry.util.ObjectBuilder;
import com.mallfoundry.util.Position;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ProductOption extends Serializable, Position {

    String getName();

    void setName(String name);

    ProductOptionValue createValue(String label);

    Optional<ProductOptionValue> getValue(String label);

    List<ProductOptionValue> getValues();

    void setValues(List<ProductOptionValue> values);

    void addValue(ProductOptionValue value);

    void removeValue(ProductOptionValue value);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<ProductOption> {

        Builder name(String name);

        Builder value(ProductOptionValue value);

        Builder values(List<ProductOptionValue> values);

        Builder values(ProductOptionValue... values);

        Builder value(String value);

        Builder values(String... values);
    }

    abstract class BuilderSupport implements Builder {

        private final ProductOption option;

        protected BuilderSupport(ProductOption option) {
            this.option = option;
        }

        @Override
        public Builder name(String name) {
            this.option.setName(name);
            return this;
        }

        @Override
        public Builder value(ProductOptionValue value) {
            this.option.addValue(value);
            return this;
        }

        @Override
        public Builder values(List<ProductOptionValue> values) {
            values.forEach(this::value);
            return this;
        }

        @Override
        public Builder values(ProductOptionValue... values) {
            this.values(List.of(values));
            return this;
        }

        @Override
        public Builder value(String label) {
            this.option.addValue(this.option.createValue(label));
            return this;
        }

        @Override
        public Builder values(String... values) {
            Arrays.stream(values).forEach(this::value);
            return this;
        }

        @Override
        public ProductOption build() {
            return this.option;
        }
    }

}
