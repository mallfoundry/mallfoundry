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
import org.mallfoundry.security.access.AccessControl;
import org.mallfoundry.security.access.AccessControlEntry;
import org.mallfoundry.security.access.AccessControlSupport;
import org.mallfoundry.security.access.Permission;
import org.mallfoundry.security.access.Principal;
import org.mallfoundry.security.access.Resource;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_access_control")
public class JpaAccessControl extends AccessControlSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = JpaResource.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id_")
    private Resource resource;

    @ManyToOne(targetEntity = JpaPrincipal.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id_")
    private Principal owner;

    @Column(name = "inherit_")
    private boolean inherit = true;

    @OneToMany(targetEntity = JpaAccessControlEntry.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "access_control_id_")
    private List<AccessControlEntry> entries = new ArrayList<>();

    @ManyToOne(targetEntity = JpaAccessControl.class)
    @JoinColumn(name = "parent_id_")
    private AccessControl parent;

    public JpaAccessControl(String id) {
        this.id = id;
    }

    public static JpaAccessControl of(AccessControl acl) {
        if (acl instanceof JpaAccessControl) {
            return (JpaAccessControl) acl;
        }
        var target = new JpaAccessControl();
        BeanUtils.copyProperties(acl, target);
        return target;
    }

    private AccessControlEntry getEntry(Principal principal) {
        var entry = this.getEntryOrNull(principal);
        if (Objects.isNull(entry)) {
            entry = new JpaAccessControlEntry(principal);
            this.entries.add(entry);
        }
        return entry;
    }

    private AccessControlEntry getEntryOrNull(Principal principal) {
        for (var entry : entries) {
            if (entry.getPrincipal().equals(principal)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public void grant(Permission permission, Principal principal) {
        this.getEntry(principal).addPermission(permission);
    }

    @Override
    public void grants(Set<Permission> permissions, Principal principal) {
        this.getEntry(principal).addPermissions(permissions);
    }

    @Override
    public void revoke(Permission permission, Principal principal) {
        this.getEntry(principal).removePermission(permission);
    }

    @Override
    public void revoke(Set<Permission> permissions, Principal principal) {
        this.getEntry(principal).removePermissions(permissions);
    }

    @Override
    public boolean granted(Permission permission, Principal principal) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkPermission(permission);
    }

    @Override
    public boolean granted(Set<Permission> permissions, Principal principal) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkAnyPermission(permissions);
    }

    @Override
    public boolean granted(Set<Permission> permissions, Set<Principal> principals) {
        for (Principal principal : principals) {
            if (this.granted(permissions, principal)) {
                return true;
            }
        }

        if (this.isInherit() && Objects.nonNull(this.getParent())) {
            return this.getParent().granted(permissions, principals);
        }

        return false;
    }
}
