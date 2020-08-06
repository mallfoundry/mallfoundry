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
import org.mallfoundry.store.role.RoleAuthority;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_role_authority")
public class JpaRoleAuthority implements RoleAuthority {

    @Id
    @Column(name = "authority_")
    private String authority;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @OneToMany(targetEntity = JpaRoleAuthority.class)
    private List<RoleAuthority> children = new ArrayList<>();

    @Column(name = "position_")
    private int position;

    public static JpaRoleAuthority of(RoleAuthority authority) {
        if (authority instanceof JpaRoleAuthority) {
            return (JpaRoleAuthority) authority;
        }
        var target = new JpaRoleAuthority();
        BeanUtils.copyProperties(authority, target);
        return target;
    }
}
