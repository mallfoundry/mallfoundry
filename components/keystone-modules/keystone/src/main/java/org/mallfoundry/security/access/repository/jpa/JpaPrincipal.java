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

package org.mallfoundry.security.access.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.PrincipalSupport;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_access_control_principal")
public class JpaPrincipal extends PrincipalSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "type_")
    private String type;

    @Column(name = "name_")
    private String name;

    public JpaPrincipal(String id) {
        this.id = id;
    }

    public JpaPrincipal(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public static JpaPrincipal of(Principal principal) {
        if (principal instanceof JpaPrincipal) {
            return (JpaPrincipal) principal;
        }
        var target = new JpaPrincipal();
        BeanUtils.copyProperties(principal, target);
        return target;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof JpaPrincipal)) {
            return false;
        }
        JpaPrincipal that = (JpaPrincipal) object;
        return Objects.equals(type, that.type)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }
}
