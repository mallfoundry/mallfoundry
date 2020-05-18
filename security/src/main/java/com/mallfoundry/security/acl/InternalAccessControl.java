package com.mallfoundry.security.acl;

import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Entity
@Table(name = "access_controls")
public class InternalAccessControl implements AccessControl {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = InternalResource.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id_")
    private Resource resource;

    @ManyToOne(targetEntity = InternalPrincipal.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id_")
    private Principal owner;

    @Column(name = "inherit_")
    private boolean inherit = true;

    @OneToMany(targetEntity = InternalAccessControlEntry.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "access_control_id_")
    private List<AccessControlEntry> entries = new ArrayList<>();

    @ManyToOne(targetEntity = InternalAccessControl.class)
    @JoinColumn(name = "parent_id_")
    private AccessControl parent;

    public static InternalAccessControl of(AccessControl acl) {
        if (acl instanceof InternalAccessControl) {
            return (InternalAccessControl) acl;
        }
        var target = new InternalAccessControl();
        BeanUtils.copyProperties(acl, target);
        return target;
    }

    private AccessControlEntry getEntry(Principal principal) {
        var entry = this.getEntryOrNull(principal);
        if (Objects.isNull(entry)) {
            entry = new InternalAccessControlEntry(principal);
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
    public void revoke(Principal principal, Permission permission) {
        this.getEntry(principal).removePermission(permission);
    }

    @Override
    public boolean granted(Principal principal, Permission permission) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkPermission(permission);
    }

    @Override
    public boolean granted(Principal principal, List<Permission> permissions) {
        var entry = this.getEntryOrNull(principal);
        return !Objects.isNull(entry) && entry.checkAnyPermission(permissions);
    }

    @Override
    public boolean granted(List<Principal> principals, List<Permission> permissions) {
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
