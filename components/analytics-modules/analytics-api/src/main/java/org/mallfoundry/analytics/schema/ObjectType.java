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

package org.mallfoundry.analytics.schema;

import org.mallfoundry.util.ObjectBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ObjectType extends ObjectBuilder.ToBuilder<ObjectType.Builder> {

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
