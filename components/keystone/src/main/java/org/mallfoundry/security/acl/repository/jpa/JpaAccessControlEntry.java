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

package org.mallfoundry.security.acl.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.acl.AccessControlEntry;
import org.mallfoundry.security.acl.AccessControlEntrySupport;
import org.mallfoundry.security.acl.Permission;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.repository.jpa.convert.PermissionSetConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_access_control_entry")
public class JpaAccessControlEntry extends AccessControlEntrySupport {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = JpaPrincipal.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id_")
    private Principal principal;

    @Convert(converter = PermissionSetConverter.class)
    @Column(name = "permissions_")
    private Set<Permission> permissions = new HashSet<>();

    public JpaAccessControlEntry(Principal principal) {
        this.principal = principal;
    }

    public static JpaAccessControlEntry of(AccessControlEntry aclEntry) {
        if (aclEntry instanceof JpaAccessControlEntry) {
            return (JpaAccessControlEntry) aclEntry;
        }
        var target = new JpaAccessControlEntry();
        BeanUtils.copyProperties(aclEntry, target);
        return target;
    }

}
