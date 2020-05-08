package com.mallfoundry.security.acl;

import com.mallfoundry.security.acl.repository.jpa.convert.PermissionListConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "acl_entries")
public class InternalAclEntry implements AclEntry {

    @Id
    @Column(name = "id_")
    private String id;

    @ManyToOne(targetEntity = InternalPrincipal.class)
    @JoinColumn(name = "principal_id_")
    private Principal principal;

    @Convert(converter = PermissionListConverter.class)
    @Column(name = "permissions_")
    private List<Permission> permissions;

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
