/*
 * Copyright (C) 2019-2021 the original author or authors.
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

package org.mallfoundry.catalog.option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.util.Positions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Getter
@Setter
@NoArgsConstructor
public abstract class OptionSupport implements MutableOption {

    public OptionSupport(String id) {
        this.setId(id);
    }

    @Override
    public OptionValue createValue(String valueId) {
        return new DefaultOptionValue(valueId);
    }

    @Override
    public OptionValue getValue(String label) {
        return this.findValue(label).orElseThrow();
    }

    @Override
    public Optional<OptionValue> findValue(String label) {
        return this.getValues().stream().filter(value -> Objects.equals(value.getLabel(), label)).findFirst();
    }

    private Optional<OptionValue> obtainValue(String id) {
        return this.getValues().stream()
                .filter(value -> Objects.equals(value.getId(), id))
                .findFirst();
    }

    private void setValue(OptionValue source, OptionValue target) {
        target.setLabel(source.getLabel());
    }

    @Override
    public void addValue(OptionValue value) {
        if (Objects.isNull(value.getId())) {
            this.getValues().add(value);
        } else {
            this.obtainValue(value.getId())
                    .ifPresentOrElse(target -> setValue(value, target), () -> this.getValues().add(value));
        }
        Positions.sort(this.getValues());
    }

    @Override
    public void addValues(List<OptionValue> values) {
        emptyIfNull(values).forEach(this::addValue);
    }

    @Override
    public void removeValue(OptionValue value) {
        this.getValues().remove(value);
    }

    @Override
    public void removeValues(List<OptionValue> values) {
        emptyIfNull(values).forEach(this::removeValue);
    }

    @Override
    public void clearValues() {
        emptyIfNull(this.getValues()).clear();
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        private final Option option;

        protected BuilderSupport(Option option) {
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
        public Builder value(OptionValue value) {
            this.option.addValue(value);
            return this;
        }

        @Override
        public Builder value(Function<Option, OptionValue> values) {
            return this.value(values.apply(this.option));
        }

        @Override
        public Builder values(List<OptionValue> values) {
            this.option.addValues(values);
            return this;
        }

        @Override
        public Builder values(OptionValue... values) {
            return this.values(List.of(values));
        }

        @Override
        public Builder values(Function<Option, List<OptionValue>> values) {
            return this.values(values.apply(this.option));
        }

        @Override
        public Builder values(Supplier<List<OptionValue>> supplier) {
            return this.values(supplier.get());
        }

        @Override
        public Builder position(int position) {
            this.option.setPosition(position);
            return this;
        }

        @Override
        public Option build() {
            return this.option;
        }
    }
}
