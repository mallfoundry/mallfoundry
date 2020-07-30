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

package org.mallfoundry.store.role.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.store.role.StoreRole;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store_role")
public class JpaStoreRole implements StoreRole {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_d_")
    private String storeId;

    @Column(name = "name_")
    private String name;

    @Convert(converter = StringListConverter.class)
    @Column(name = "authorities_", length = 1024 * 2)
    private List<String> authorities;

    public JpaStoreRole(String id) {
        this.id = id;
    }

    public static JpaStoreRole of(StoreRole role) {
        if (role instanceof JpaStoreRole) {
            return (JpaStoreRole) role;
        }
        var target = new JpaStoreRole();
        BeanUtils.copyProperties(role, target);
        return target;
    }
}
