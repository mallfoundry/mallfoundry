package org.mallfoundry.security.acl.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.security.acl.AccessControl;
import org.mallfoundry.security.acl.AccessControlEntry;
import org.mallfoundry.security.acl.AccessControlSupport;
import org.mallfoundry.security.acl.InternalResource;
import org.mallfoundry.security.acl.Permission;
import org.mallfoundry.security.acl.Principal;
import org.mallfoundry.security.acl.Resource;
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

    @ManyToOne(targetEntity = InternalResource.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id_")
    private Resource resource;

    @ManyToOne(targetEntity = JpaPrincipal.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id_")
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
    public void grant(Principal principal, Permission permission) {
        this.getEntry(principal).addPermission(permission);
    }

    @Override
    public void grants(Principal principal, Set<Permission> permissions) {
        this.getEntry(principal).addPermissions(permissions);
    }

    @Override
    public void revoke(Principal principal, Permission permission) {
        this.getEntry(principal).removePermission(permission);
    }

    @Override
    public void revoke(Principal principal, Set<Permission> permissions) {
        this.getEntry(principal).removePermissions(permissions);
    }

    @Override
    public boolean granted(Principal principal, Permission permission) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkPermission(permission);
    }

    @Override
    public boolean granted(Principal principal, Set<Permission> permissions) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkAnyPermission(permissions);
    }

    @Override
    public boolean granted(Set<Principal> principals, Set<Permission> permissions) {
        for (Principal principal : principals) {
            if (this.granted(principal, permissions)) {
                return true;
            }
        }

        if (this.isInherit() && Objects.nonNull(this.getParent())) {
            return this.getParent().granted(principals, permissions);
        }

        return false;
    }
}
