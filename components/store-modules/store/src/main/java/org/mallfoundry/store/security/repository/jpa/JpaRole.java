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

package org.mallfoundry.store.security.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.data.repository.jpa.convert.StringSetConverter;
import org.mallfoundry.store.security.Role;
import org.mallfoundry.store.security.RoleId;
import org.mallfoundry.store.security.RoleSupport;
import org.mallfoundry.store.security.RoleType;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store_role")
public class JpaRole extends RoleSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    @NotBlank
    @Column(name = "tenant_id_")
    private String tenantId;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @NotNull
    @Convert(converter = StringSetConverter.class)
    @Column(name = "authorities_", length = 1024 * 4)
    private Set<String> authorities = new HashSet<>();

    @NotBlank
    @Column(name = "description_")
    private String description;

    @NotNull
    @Column(name = "type_")
    private RoleType type;

    @Min(0)
    @Column(name = "staffs_count_")
    private int staffsCount;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaRole(RoleId id) {
        this.tenantId = id.getTenantId();
        this.storeId = id.getStoreId();
        this.id = id.getId();
    }

    public static JpaRole of(Role role) {
        if (role instanceof JpaRole) {
            return (JpaRole) role;
        }
        var target = new JpaRole();
        BeanUtils.copyProperties(role, target);
        return target;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaRole)) {
            return false;
        }
        JpaRole jpaRole = (JpaRole) object;
        return Objects.equals(id, jpaRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
