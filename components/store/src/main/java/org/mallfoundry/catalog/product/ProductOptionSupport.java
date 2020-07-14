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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.util.Positions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductOptionSupport implements MutableProductOption {

    public ProductOptionSupport(String id) {
        this.setId(id);
    }

    @Override
    public ProductOptionValue createValue(String valueId) {
        return new DefaultProductOptionValue(valueId);
    }

    @Override
    public Optional<ProductOptionValue> getValue(String label) {
        return this.getValues().stream().filter(value -> Objects.equals(value.getLabel(), label)).findFirst();
    }

    @Override
    public void addValue(ProductOptionValue value) {
        this.getValues().add(value);
        Positions.sort(this.getValues());
    }

    @Override
    public void addValues(List<ProductOptionValue> values) {

    }

    @Override
    public void removeValue(ProductOptionValue value) {
        this.getValues().remove(value);
    }

    @Override
    public void removeValues(List<ProductOptionValue> values) {

    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final ProductOption option;

        protected BuilderSupport(ProductOption option) {
            this.option = option;
        }

        @Override
        public Builder id(String id) {
            this.option.setId(id);
            return this;
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
            this.option.addValues(values);
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
        public Builder values(Supplier<List<ProductOptionValue>> supplier) {
            return this.values(supplier.get());
        }

        @Override
        public Builder position(int position) {
            this.option.setPosition(position);
            return this;
        }

        @Override
        public ProductOption build() {
            return this.option;
        }
    }
}
