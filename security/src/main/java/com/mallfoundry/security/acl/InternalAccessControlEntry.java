package com.mallfoundry.security.acl;

import com.mallfoundry.security.acl.repository.jpa.convert.PermissionListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "access_control_entries")
public class InternalAccessControlEntry implements AccessControlEntry {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = InternalPrincipal.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "principal_id_")
    private Principal principal;

    @Convert(converter = PermissionListConverter.class)
    @Column(name = "permissions_")
    private List<Permission> permissions = new ArrayList<>();

    public InternalAccessControlEntry(Principal principal) {
        this.principal = principal;
    }

    public static InternalAccessControlEntry of(AccessControlEntry aclEntry) {
        if (aclEntry instanceof InternalAccessControlEntry) {
            return (InternalAccessControlEntry) aclEntry;
        }
        var target = new InternalAccessControlEntry();
        BeanUtils.copyProperties(aclEntry, target);
        return target;
    }

    @Override
    public void addPermission(Permission permission) {
        permission = InternalPermission.of(permission);
        if (!this.checkPermission(permission)) {
            this.permissions.add(permission);
        }
    }

    @Override
    public void removePermission(Permission permission) {
        this.permissions.remove(InternalPermission.of(permission));
    }

    @Override
    public boolean checkPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    @Override
    public boolean checkAnyPermission(List<Permission> permissions) {
        return CollectionUtils.containsAny(this.permissions, permissions);
    }
}
