package com.mallfoundry.catalog;

import com.mallfoundry.util.ObjectBuilder;
import com.mallfoundry.util.Position;

import java.io.Serializable;

public interface ProductOptionValue extends Serializable, Position {

    String getId();

    String getLabel();

    void setLabel(String label);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<ProductOptionValue> {
        Builder label(String label);
    }

    abstract class BuilderSupport implements Builder {

        private final ProductOptionValue value;

        protected BuilderSupport(ProductOptionValue value) {
            this.value = value;
        }

        @Override
        public Builder label(String label) {
            this.value.setLabel(label);
            return this;
        }

        @Override
        public ProductOptionValue build() {
            return this.value;
        }
    }

}
