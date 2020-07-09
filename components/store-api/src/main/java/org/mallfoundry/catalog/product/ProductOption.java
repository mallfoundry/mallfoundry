/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.catalog.product;

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProductOption extends Serializable, Position {

    String getId();

    void setId(String id);

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
