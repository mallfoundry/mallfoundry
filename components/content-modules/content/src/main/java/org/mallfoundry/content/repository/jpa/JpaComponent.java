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

package org.mallfoundry.content.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.content.Component;
import org.mallfoundry.content.ComponentCompatibility;
import org.mallfoundry.content.ComponentModule;
import org.mallfoundry.content.ComponentOwner;
import org.mallfoundry.content.ComponentSupport;
import org.mallfoundry.data.repository.jpa.convert.StringObjectMapConverter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_component")
public class JpaComponent extends ComponentSupport {

    @Id
    @Column(name = "key_")
    private String key;

    @Convert(converter = StringObjectMapConverter.class)
    @Column(name = "properties_")
    private Map<String, Object> properties = new HashMap<>();

    @OneToMany(targetEntity = JpaComponent.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_key_")
    private List<Component> children = new ArrayList<>();

    @Column(name = "name_")
    private String name;

    @Transient
    private ComponentOwner owner;

    @Transient
    private ComponentModule module;

    @Transient
    private List<ComponentCompatibility> compatibilities;
}
