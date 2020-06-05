package org.mallfoundry.catalog.product;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProductOption extends Serializable, Position {

    String getId();

    String getName();

    void setName(String name);

    ProductOptionValue createValue(String valueId);

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

        Builder value(Function<ProductOption, ProductOptionValue> value);

        Builder values(List<ProductOptionValue> values);

        Builder values(ProductOptionValue... values);

        Builder values(Function<ProductOption, List<ProductOptionValue>> values);

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
        public Builder value(Function<ProductOption, ProductOptionValue> values) {
            return this.value(values.apply(this.option));
        }

        @Override
        public Builder values(List<ProductOptionValue> values) {
            values.forEach(this::value);
            return this;
        }

        @Override
        public Builder values(ProductOptionValue... values) {
            return this.values(List.of(values));
        }

        @Override
        public Builder values(Function<ProductOption, List<ProductOptionValue>> values) {
            return this.values(values.apply(this.option));
        }

        @Override
        public ProductOption build() {
            return this.option;
        }
    }

}
