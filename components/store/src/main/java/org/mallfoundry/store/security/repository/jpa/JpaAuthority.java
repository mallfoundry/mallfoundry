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
import org.mallfoundry.store.security.Authority;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_store_authority")
public class JpaAuthority implements Authority {

    @Id
    @Column(name = "authority_")
    private String authority;

    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @OneToMany(targetEntity = JpaAuthority.class)
    @JoinColumn(name = "parent_id_")
    private List<Authority> children = new ArrayList<>();

    @Column(name = "position_")
    private int position;

    public static JpaAuthority of(Authority authority) {
        if (authority instanceof JpaAuthority) {
            return (JpaAuthority) authority;
        }
        var target = new JpaAuthority();
        BeanUtils.copyProperties(authority, target);
        return target;
    }
}
