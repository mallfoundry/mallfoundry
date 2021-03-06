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

package org.mallfoundry.analytics.schema.repository.jpa.convert;

import org.mallfoundry.analytics.schema.DefaultObjectField;
import org.mallfoundry.analytics.schema.ObjectField;
import org.mallfoundry.util.JsonUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ObjectFieldListConverter implements AttributeConverter<List<ObjectField>, String> {
    @Override
    public String convertToDatabaseColumn(List<ObjectField> attribute) {
        return Objects.isNull(attribute) ? null : JsonUtils.stringify(attribute);
    }

    @Override
    public List<ObjectField> convertToEntityAttribute(String dbData) {
        return ObjectUtils.isEmpty(dbData) ? new LinkedList<>()
                : JsonUtils.parse(dbData, LinkedList.class, DefaultObjectField.class);
    }
}
