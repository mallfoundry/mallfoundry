package com.mallfoundry.security.acl;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "acl")
public class InternalAcl implements Acl {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = InternalResource.class)
    @JoinColumn(name = "resource_id_")
    private Resource resource;

    @ManyToOne(targetEntity = InternalPrincipal.class)
    @JoinColumn(name = "principal_id_")
    private Principal owner;

    @Column(name = "inherit_")
    private boolean inherit = true;

    @OneToMany(targetEntity = InternalAclEntry.class)
    @JoinColumn(name = "acl_id_")
    private List<AclEntry> entries;

    @ManyToOne(targetEntity = InternalAcl.class)
    @JoinColumn(name = "parent_id_")
    private Acl parent;

    private AclEntry getEntry(Principal principal) {
        var entry = this.getEntryOrNull(principal);
        if (Objects.isNull(entry)) {
            entry = new InternalAclEntry();
            this.entries.add(entry);
        }
        return entry;
    }

    private AclEntry getEntryOrNull(Principal principal) {
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
        return false;
    }
}
