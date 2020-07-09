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

package org.mallfoundry.analytics.schema.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.analytics.schema.ObjectField;
import org.mallfoundry.analytics.schema.ObjectType;
import org.mallfoundry.analytics.schema.ObjectTypeSupport;
import org.mallfoundry.analytics.schema.repository.jpa.convert.ObjectFieldListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_object_type")
public class JpaObjectType extends ObjectTypeSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "name_")
    private String name;

    @Convert(converter = ObjectFieldListConverter.class)
    @Column(name = "fields_", length = 1024 * 2)
    private List<ObjectField> fields = new LinkedList<>();

    public JpaObjectType(String id) {
        this.id = id;
    }

    public static JpaObjectType of(ObjectType objectType) {
        if (objectType instanceof JpaObjectType) {
            return (JpaObjectType) objectType;
        }
        var target = new JpaObjectType();
        BeanUtils.copyProperties(objectType, target);
        return target;
    }
}
