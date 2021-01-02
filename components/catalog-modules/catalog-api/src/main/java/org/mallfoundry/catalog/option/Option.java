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

import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;
import org.mallfoundry.util.PositionBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Option extends Serializable, Position, ObjectBuilder.ToBuilder<Option.Builder> {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    OptionValue createValue(String valueId);

    OptionValue getValue(String label);

    Optional<OptionValue> findValue(String label);

    List<OptionValue> getValues();

    void addValue(OptionValue value);

    void addValues(List<OptionValue> values);

    void removeValue(OptionValue value);

    void removeValues(List<OptionValue> values);

    void clearValues();

    interface Builder extends ObjectBuilder<Option>, PositionBuilder<Builder> {

        Builder id(String id);

        Builder name(String name);

        Builder value(OptionValue value);

        Builder value(Function<Option, OptionValue> value);

        Builder values(List<OptionValue> values);

        Builder values(OptionValue... values);

        Builder values(Function<Option, List<OptionValue>> function);

        Builder values(Supplier<List<OptionValue>> supplier);
    }
}
